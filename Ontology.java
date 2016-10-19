
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
	
		static class TopicTreeNode {
			
			String topic;
			Set<String> questions;
			List<TopicTreeNode> children;
			TopicTreeNode parent;
			
			public TopicTreeNode(
					String topic, 
					TopicTreeNode parent) 
			{
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
		
		static List<String> processChildrenTrees(String flattenedTree) {
			List<String> childrenTrees = new ArrayList<>();
			Stack<Character> paranthesesMatching = new Stack<>();
			int prevSpaceIndex = 0;
			for (int i = 0; i < flattenedTree.length(); i++) {
				if (paranthesesMatching.isEmpty() && 
					flattenedTree.charAt(i) == ' ') {
					childrenTrees.add(flattenedTree.substring(prevSpaceIndex, i));
					prevSpaceIndex = i+1;
				}
				if (flattenedTree.charAt(i) == '(') {
					paranthesesMatching.push(flattenedTree.charAt(i));
				}
				if (flattenedTree.charAt(i) == ')') {
					paranthesesMatching.pop();
				}
			}
			childrenTrees.add(flattenedTree.substring(prevSpaceIndex));
			return childrenTrees;
		}
		
		static TopicTreeNode buildTopicOntologyTreeHelper(
				TopicTreeNode parent, 
				String flattenedSubTree, 
				Map<String, TopicTreeNode> linkedTopicTreeMap) 
		{
			
			String topic;
			List<TopicTreeNode> children = new ArrayList<>();
			List<String> childrenTrees = new ArrayList<>();
			
			int indexBeforeChildren = flattenedSubTree.indexOf('(');
			if (indexBeforeChildren == -1) {
				topic = flattenedSubTree;
			} else {
				topic = flattenedSubTree.substring(0, indexBeforeChildren);
				String childrenFlattenedTree = 
						flattenedSubTree.substring(indexBeforeChildren+2, flattenedSubTree.length()-2);
				childrenTrees = processChildrenTrees(childrenFlattenedTree);	
			}
			TopicTreeNode node = new TopicTreeNode(topic, parent);
			if (!childrenTrees.isEmpty()) {
				for (String childTree : childrenTrees) {
					children.add(buildTopicOntologyTreeHelper(node, childTree, linkedTopicTreeMap));
				}
			}
			node.children = children;
			linkedTopicTreeMap.put(topic, node);
			return node;
		}
		
		// Animals( Reptiles Birds( Eagles Pigeons Crows ) )
		// Animals -> ( Reptiles Birds( Eagles Pigeons Crows ) )
		static Map<String, TopicTreeNode> buildTopicOntology(String flattenedTree) {
			Map<String, TopicTreeNode> linkedTopicTreeMap = new HashMap<>();
			flattenedTree = flattenedTree.replace(" (", "(");
			buildTopicOntologyTreeHelper(null, flattenedTree, linkedTopicTreeMap);
			return linkedTopicTreeMap;
		}
		
		static void addQuestionToTopic(
				Map<String, TopicTreeNode> topicOntologyLinkedTree,
				String topicQuestion) 
		{
			int indexOfColon = topicQuestion.indexOf(':');
			String topic = topicQuestion.substring(0,indexOfColon);
			String question = topicQuestion.substring(indexOfColon+2);
			topicOntologyLinkedTree.get(topic).addQuestion(question);
		}
		
		static int processQuery(
				Map<String, TopicTreeNode> topicOntologyLinkedTree,
				String query) 
		{
			int indexOfSpace = query.indexOf(' ');
			String topic = query.substring(0,indexOfSpace);
			String questionPrefix = query.substring(indexOfSpace+1);
			
			int queryCount = 0;		
			for (String question : topicOntologyLinkedTree.get(topic).questions) {
				if (question.startsWith(questionPrefix)) {
					queryCount++;
				}
			}
			return queryCount;
		}
		
		static List<Integer> doOntology (
				String flattenedTree, 
				List<String> questions, 
				List<String> queries) 
		{
			List<Integer> queryResults = new ArrayList<>();
			Map<String, TopicTreeNode> topicOntology = buildTopicOntology(flattenedTree);
			for (String question: questions) {
				addQuestionToTopic(topicOntology, question);
			}
			for (String query: queries) {
				queryResults.add(processQuery(topicOntology, query));
			}
			return queryResults;
		}

		public static void main(String[] args) {
			String testProcessChildren1 = "Reptiles Birds( Eagles Pigeons Crows )";
			String testProcessChildren2 = "Reptiles Birds Eagles Monkeys Hippos";
			String testProcessChildren3 = 
					"Reptiles Birds Eagles Monkeys Hippos( Bats Lemurs( Dogs Cats ) )";
			String testProcessChildren4 = 
					"Reptiles( Bats Lemurs( Dogs Cats ) ) Birds Eagles Monkeys Hippos( Bats Lemurs( Dogs Cats ) )";
			String testProcessChildren5 = "Animals( Reptiles Birds( Eagles Pigeons Crows ) )";
			System.out.println(processChildrenTrees(testProcessChildren1));
			System.out.println(processChildrenTrees(testProcessChildren2));
			System.out.println(processChildrenTrees(testProcessChildren3));
			System.out.println(processChildrenTrees(testProcessChildren4));
			System.out.println(processChildrenTrees(testProcessChildren5));
			
			System.out.println();
			System.out.println(buildTopicOntology("Animals ( Reptiles Birds ( Eagles Pigeons Crows ) )"));
			
			System.out.println();
			String flattenedTree = "Animals ( Reptiles Birds ( Eagles Pigeons Crows ) )";
			List<String> questions = Arrays.asList(
					"Reptiles: Why are many reptiles green?",
					"Birds: How do birds fly?",
					"Eagles: How endangered are eagles?",
					"Pigeons: Where in the world are pigeons most densely populated?",
					"Eagles: Where do most eagles live?");
			List<String> queries = Arrays.asList(
					"Eagles How en",
					"Birds Where",
					"Reptiles Why do",
					"Animals Wh");
			System.out.println(doOntology(flattenedTree, questions, queries));
			
		}
}
