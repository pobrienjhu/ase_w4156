#!/usr/bin/python

import base64
import hashlib
import hmac
import json
import struct
import threading
import urllib2


BASE_URL = 'http://localhost:8080/'
LOGIN_URL = '%s%s' % (BASE_URL, 'apiLogin.do')
GET_EVENT_URL = '%s%s' % (BASE_URL, '/app/getEvent.do')
VOTE_URL = '%s%s' % (BASE_URL, 'app/voteEvent.do')

USER_ID = 1
EVENT_ID = 1
NUMBER_ITERATIONS = 10

"""
To seed the database with an event containing many categories, run the
following snippet on the Chrome inspector console (don't forget to set
up the event date/times and descriptions before submitting):

(function() {
  var num_threads = 10;
  var dummyEvt = {};
  dummyEvt.preventDefault = function() {};
  removeCategory(1, dummyEvt);
  for (var i = 0; i < 30; i++) {
    addCategory(dummyEvt);
    for (var j = 2; j < num_threads; j++) {
      addOption(i + 1);
    }
    $('#catg' + (i + 1) + 'Name')[0].value = 'Category ' + (i + 1);
    $('#catg' + (i + 1) + 'Desc')[0].value = 'Description';
    for (var j = 0; j < num_threads; j++) {
      $('#catg' + (i + 1) + 'op' + (j + 1) + 'Value')[0].value = 'Option ' + (j + 1);
    }
  }
})();
"""

class Tester(threading.Thread):

  def __init__(self, **kwargs):
    threading.Thread.__init__(self)
    self.thread_id = kwargs['tid']
    self.event_id = kwargs['event_id']
    self.categories = kwargs['categories']
    self.option_size = kwargs['option_size']
    self.sid = kwargs['sid']
    self.csrf = kwargs['csrf']

  def run(self):
    values = [ str(self.event_id) ]
    for i in range(0, self.categories):
      values.append(str(1 + (i * self.option_size) + self.thread_id))
    vote_str = ' '.join(values)
    for i in range(0, NUMBER_ITERATIONS):
      request = urllib2.Request(VOTE_URL)
      request.add_header('Cookie', 'JSESSIONID=%s' % (self.sid))
      request.add_header('X-CSRF-TOKEN', self.csrf)
      request.data = vote_str
      try:
        response = urllib2.urlopen(request)
      except IOError, e:
        print 'tid %d, error: %s' % (self.thread_id, e)

class RedirectHandler(urllib2.HTTPRedirectHandler):
  def http_error_302(self, req, fp, code, msg, hdrs):
    for header in hdrs:
      if header.lower() == 'set-cookie':
        fields = hdrs.get(header).split(';')
        self.cookie = fields[0].split('=', 2)[1]

    return urllib2.HTTPRedirectHandler.http_error_302(self, req, fp, code, msg,
                                                    hdrs)
def generatePassword(key, user_id):
  value = struct.pack('>Q', user_id)
  h = hmac.new(key.encode(), digestmod=hashlib.sha256)
  h.update(value)
  mac = h.digest()
  return mac

def generateApiKey(key, user_id):
  password = base64.b64encode(generatePassword(key, user_id))
  value = '%s:%s' % (str(user_id), password)
  return base64.b64encode(value)

def sanityCheckEvent(event):
  categories = event['voteCategories']
  expectedOptionSize = len(categories[0]['voteOptions'])

  for category in categories:
    if len(category['voteOptions']) != expectedOptionSize:
      raise Exception('one of the vote categories has an option mismatch')
  return (expectedOptionSize, len(categories))

def getEvent(sessionid):
  request = urllib2.Request('%s?eventId=%d' % (GET_EVENT_URL, EVENT_ID))
  request.add_header('Cookie', 'JSESSIONID=%s' % (sessionid))
  try:
    response = urllib2.urlopen(request)
    data = ''
    for line in response:
      data += line
    return json.loads(data)
  except IOError, e:
    print 'error: %s' % (e)

def login(api_key):
  data = 'method=api&token=%s' % (api_key)
  request = urllib2.Request(LOGIN_URL, data)
  redirector = RedirectHandler()
  opener = urllib2.build_opener(redirector)
  urllib2.install_opener(opener)
  try:
    response = urllib2.urlopen(request)
    header  = (response.info().getfirstmatchingheader('X-Csrf-Token'))
    csrf = header[0].split(':', 2)[1].strip()
    return (redirector.cookie, csrf)
  except IOError, e:
    print 'error: %s' % (e)

api_key = generateApiKey('secret', USER_ID)
(sessionid, csrf) = login(api_key)
evt = getEvent(sessionid)
(nr_options, nr_categories) = sanityCheckEvent(evt)

nr_threads = nr_options

for i in range(0, nr_threads):
  t = Tester(sid=sessionid, csrf=csrf, tid=i, categories=nr_categories,
             option_size=nr_options, event_id=EVENT_ID)
  t.start()
