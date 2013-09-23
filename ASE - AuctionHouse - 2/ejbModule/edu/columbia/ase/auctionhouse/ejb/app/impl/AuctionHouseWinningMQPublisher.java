package edu.columbia.ase.auctionhouse.ejb.app.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseWinningPublisher;
import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.WinningAuctionMessage;

/**
 * Session Bean implementation class AuctionHouseWinningPublisher
 */
@Startup
@Singleton
public class AuctionHouseWinningMQPublisher implements AuctionHouseWinningPublisher {

	private Logger log = LoggerFactory.getLogger(AuctionHouseWinningMQPublisher.class);

	/**
	 * MQ Publisher code
	 */

    private Connection connection;
    private Session session;
    private MessageProducer publisher;
    private Topic topic;

    private String url = "tcp://localhost:61616";
    
    /**
     * Default constructor. 
     */
    public AuctionHouseWinningMQPublisher() {
        // TODO Auto-generated constructor stub
    }
    
	@PostConstruct
	public void startMQPublisher() throws JMSException{
		
		log.info("Connecting to the MQ Server!");
		
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic("aseAuctionHouse.auctionWonMessages");

        publisher = session.createProducer(topic);
        publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        
        connection.start();
        
		log.info("Successfully Connected to the MQ Server!");

	}
    
	@Override
	public void sendWinningMessage(int customerId, Auction auction) throws Exception {
		
		WinningAuctionMessage winningAuctionMsg = new WinningAuctionMessage(customerId, auction.getAuctionId(), auction.getCurrentPrice());
		
		publisher.send(session.createObjectMessage(winningAuctionMsg));
		
	}
	
	
}
