package se.sciion.GBT;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import se.sciion.GBT.nodes.BehaviourNode;
import se.sciion.GBT.nodes.CompositeNode;
import se.sciion.GBT.nodes.DecoratorNode;

public class BehaviourTree {

	private BehaviourNode root;
	
	public BehaviourTree() {
		
	}
	
	public BehaviourTree(BehaviourNode root) {
		this.root = root;
	}
	
	public BehaviourStatus tick() {
		return root.tick();
	}

	public BehaviourTree prototype() {
		return new BehaviourTree(root.replicate());
	}
	
	public void mutate(){
		root.mutate();
	}
	
	// Also known as: Swap random sub-trees
	public void crossover(BehaviourTree tree){
		ArrayList<BehaviourNode> n1 = new ArrayList<BehaviourNode>();
		ArrayList<BehaviourNode> n2 = new ArrayList<BehaviourNode>();
		
		root.getNodes(n1);
		tree.root.getNodes(n2);
		
		// Pick random node from each tree
		BehaviourNode r1 = n1.get((int)(Math.random()*n1.size()));
		BehaviourNode r2 = n2.get((int)(Math.random()*n2.size()));
		
		// Get parents, hard to replace without at least one tree reference
		BehaviourNode pr1 = r1.getParent();
		BehaviourNode pr2 = r2.getParent();
		
		
		if(pr1 instanceof CompositeNode) {
			((CompositeNode) pr1).replaceChild(r1, r2);
		} 
		else if( pr1 instanceof DecoratorNode) {
			((DecoratorNode) pr1).setChild(r2);
		}
		// Root node is selected for crossover
		else {
			root = r1;
			root.setParent(null);
		}
		
		if(pr2 instanceof CompositeNode) {
			((CompositeNode) pr2).replaceChild(r2, r1);
		} 
		else if(pr2 instanceof DecoratorNode) {
			((DecoratorNode) pr2).setChild(r1);
		}
		// Root node is selected for crossover
		else {
			tree.root = r1;
			tree.root.setParent(null);
		}

	}
	
	public void randomize(){
		root = Prototypes.randomPrototype().randomize();
	}
	
	public void toXML(StreamResult result) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			Element e = doc.createElement("BehaviorTree");
			e.appendChild(root.toXML(doc));
			doc.appendChild(e);
			
			DOMSource source = new DOMSource(doc);
			
			TransformerFactory factory =  TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void fromXML(InputStream input){
		try {
			DocumentBuilder docBuilder;
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(input);

			Element treeElement =  (Element) doc.getFirstChild();
			NodeList list = treeElement.getChildNodes();
			for(int i = 0; i < list.getLength(); i++){
			
				if(list.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element rootElement = (Element) list.item(i);
					root = Prototypes.getPrototype(rootElement.getTagName()).replicate();
					root.fromXML(rootElement);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return root.toString();
	}

	public List<BehaviourNode> flatten() {
		ArrayList<BehaviourNode> nodes = new ArrayList<BehaviourNode>();
		root.getNodes(nodes);;
		return nodes;
	}

}
