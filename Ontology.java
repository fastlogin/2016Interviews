
import java.util.*;

/**
 * Quora Coding Challenge, Ontology
 * 
 * Quora has many questions on different topics, and a common product use-case for
 * our @mention selectors and search service is to look-up questions under a certain
 * topic as quickly as possible.
 * 
 * For this problem, imagine a simplified version of Quora where each question has only
 * one topic associated with it. In turn, the topics form a simplified ontology where
 * each topic has a list of children, and all topics are descendants of a single root
 * topic.
 * 
 * Design a system that allows for FAST searches of questions under topics. There are N
 * topics, M questions, and K queries, given in this order. Each query has a desired topic
 * as well as a desired string prefix. For each query, return the number of questions that
 * fall under the queried topic and begin with the desired string. When considering topics,
 * we want to include all descendants of the queried topic as well as the queried topic itself.
 * In other words, each query searches over the subtree of the topic.
 * 
 * The topic ontology is given in the form of a flattened tree of topic names, where each topic
 * may optionally have children. If a topic has children, they are listed after it within parantheses,
 * and those topics may have children of their own, etc. See the sample for the exact input format.
 * The tree is guaranteed to have a single root topic.
 * 
 * For ease of parsing, each topic name will be composed of English alphabetical characters,
 * and each question and query text will be composed of English alphabetical characters, spaces,
 * and question marks. Each question and query text will be well behaved: there will be no
 * consecutive spaces or leading/trailing spaces. All queries, however, are case sensitive.
 * 
 * @author George Ding
 * @url https://www.quora.com/challenges
 */
public class Ontology {
	
		class TopicTreeNode {
			
			String topic;
			Set<String> questions;
			List<TopicTreeNode> children;
			TopicTreeNode parent;
			
			public TopicTreeNode(String topic, TopicTreeNode parent) {
				this.topic = topic;
				questions = new HashSet<>();
				children = new ArrayList<>();
				this.parent = parent;
			}
			
			void addQuestion(String question) {
				this.questions.add(question);
				TopicTreeNode currNode = this.parent;
				while (currNode != null) {
					currNode.questions.add(question);
					currNode = currNode.parent;
				}
			}
		}
		
		static Map<String, TopicTreeNode> buildTopicOntology(String flattenedTree) {
			Map<String, TopicTreeNode> linkedTopicTreeMap = new HashMap<>();
			return linkedTopicTreeMap;
		}
		

		public static void main(String[] args) {
			System.out.println("Hello World!");
		}
}
