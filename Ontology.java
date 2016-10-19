
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
 * @date Wednesday, October 19, 2016
 */
public class Ontology {
	
		/**
		 * Class to represent a node in a topic ontology tree.
		 * Holds the topic it represents, a set of questions in the topic
		 * (accounts for all children topics), a list of children nodes,
		 * and a pointer to its parent node.
		 * 
		 * @author George Ding
		 */
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
			
			/**
			 * DFS bubble up to the root topic when adding a question so that
			 * all parent topics are also given ownership of the question.
			 * 
			 * @param question: Question
			 */
			void addQuestion(String question) {
				this.questions.add(question);
				TopicTreeNode currNode = this.parent;
				while (currNode != null) {
					currNode.questions.add(question);
					currNode = currNode.parent;
				}
			}
		}

		/**
		 * Utility method to take a string representing a collection of children and
		 * return a list of the string split up into the individual flattened subtrees
		 * of the children each represented as a string.
		 * 
		 * EX) "Reptiles Birds( Eagles Pigeons Crows )" -> ["Reptiles", "Birds( Eagles Pigeons Crows )"]
		 * 
		 * @param flattenedTree: Flattened tree as String
		 * @return List of children flattened trees as Strings
		 */
		static List<String> processChildrenTrees(String flattenedTree) {

			List<String> childrenTrees = new ArrayList<>();
			// Keep a stack to validate parantheses as to not count spaces inside
			// nested parantheses when splitting.
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

			// Add last child tree, loop only covers up to second to last.
			childrenTrees.add(flattenedTree.substring(prevSpaceIndex));
			return childrenTrees;
		}

		/**
		 * Recursive helper function for building a topic ontology tree.
		 * 
		 * @param parent: Parent node of the current node that is being built
		 * @param flattenedSubTree: String representation of the flattened tree to be built
		 * @param linkedTopicTreeMap: Linked tree map to mutate
		 * @return The built tree, a pointer to its root
		 */
		static TopicTreeNode buildTopicOntologyTreeHelper(
				TopicTreeNode parent,
				String flattenedSubTree,
				Map<String, TopicTreeNode> linkedTopicTreeMap)
		{

			// Initialize variables
			String topic;
			List<TopicTreeNode> children = new ArrayList<>();
			List<String> childrenTrees = new ArrayList<>();

			int indexBeforeChildren = flattenedSubTree.indexOf('('); // Topic of the current node/root
			if (indexBeforeChildren == -1) {
				topic = flattenedSubTree; // If there is no '(' in the string then the topic has no children.
			} else {
				topic = flattenedSubTree.substring(0, indexBeforeChildren);
				String childrenFlattenedTree =
						flattenedSubTree.substring(indexBeforeChildren+2, flattenedSubTree.length()-2);
				childrenTrees = processChildrenTrees(childrenFlattenedTree); // Get flattened tree strings for children.	
			}

			TopicTreeNode node = new TopicTreeNode(topic, parent);
			if (!childrenTrees.isEmpty()) {
				for (String childTree : childrenTrees) {
					children.add(buildTopicOntologyTreeHelper(node, childTree, linkedTopicTreeMap));
				}
			}

			// Set children
			node.children = children;

			// Map this node to its topic in our linked tree map.
			linkedTopicTreeMap.put(topic, node);
			return node;
		}

		/**
		 * Wrapper function to build and return the linked tree map representation of
		 * a topic ontology represented by a flattened tree. We are only interested in
		 * keeping our linked tree map in memory and not a pointer to the root of the tree
		 * because it allows us to access nodes in constant time, O(1), while abstracting
		 * out the structure of the topic ontology tree to be hidden in RAM.
		 * 
		 * @param flattenedTree: String representation of the topic ontology to be built.
		 * @return The linked tree map of the topic ontology tree
		 */
		static Map<String, TopicTreeNode> buildTopicOntology(String flattenedTree) {

			Map<String, TopicTreeNode> linkedTopicTreeMap = new HashMap<>();

			// Get rid of spaces before opening parantheses for easy separation between
			// current topic and its children.
			flattenedTree = flattenedTree.replace(" (", "(");
			buildTopicOntologyTreeHelper(null, flattenedTree, linkedTopicTreeMap);
			return linkedTopicTreeMap;
		}

		/**
		 * Given the linked tree map of a topic ontology, add a question to a given
		 * topic.
		 * 
		 * @param topicOntologyLinkedTree: The topic ontology
		 * @param topicQuestion: "Topic: Question"
		 */
		static void addQuestionToTopic(
				Map<String, TopicTreeNode> topicOntologyLinkedTree,
				String topicQuestion)
		{
			int indexOfColon = topicQuestion.indexOf(':');
			String topic = topicQuestion.substring(0,indexOfColon);
			String question = topicQuestion.substring(indexOfColon+2);
			topicOntologyLinkedTree.get(topic).addQuestion(question);
		}

		/**
		 * Given the linked tree map of a topic ontology, compute a query so as to
		 * how many questions in a given topic start with a certain question prefix.
		 * 
		 * @param topicOntologyLinkedTree: The topic ontology
		 * @param query: "Topic Question Prefix"
		 * @return The number of questions in the topic that start with a given question prefix
		 */
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

		/**
		 * Main wrapper function that performs the ontology queries. Final function that
		 * is called when generating the query results to print to STDOUT. Given a flattened
		 * tree of a topic ontology, a list of questions, and a list of queries, we will compute
		 * each query.
		 * 
		 * @param flattenedTree: The topic ontology as a flattened tree
		 * @param questions: A list of questions. Formatted: "Topic: Question"
		 * @param queries A list of queries. Formatted: "Topic Question Prefix"
		 * @return A list of all the query results in order
		 */
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
////
//
// Unit Tests for processChildrenTrees
//
//			String testProcessChildren1 =
//					"Reptiles Birds( Eagles Pigeons Crows )";
//			String testProcessChildren2 =
//					"Reptiles Birds Eagles Monkeys Hippos";
//			String testProcessChildren3 =
//					"Reptiles Birds Eagles Monkeys Hippos( Bats Lemurs( Dogs Cats ) )";
//			String testProcessChildren4 =
//					"Reptiles( Bats Lemurs( Dogs Cats ) ) Birds Eagles Monkeys Hippos( Bats Lemurs( Dogs Cats ) )";
//			String testProcessChildren5 =
//					"Animals( Reptiles Birds( Eagles Pigeons Crows ) )";
//			System.out.println(processChildrenTrees(testProcessChildren1));
//			System.out.println(processChildrenTrees(testProcessChildren2));
//			System.out.println(processChildrenTrees(testProcessChildren3));
//			System.out.println(processChildrenTrees(testProcessChildren4));
//			System.out.println(processChildrenTrees(testProcessChildren5));

////
//
// Unit test for buildTopicOntology
//
//			System.out.println();
//			System.out.println(buildTopicOntology("Animals ( Reptiles Birds ( Eagles Pigeons Crows ) )"));
			
////
//
// Unit test for doOntology
//
//			System.out.println();
//			String flattenedTree = "Animals ( Reptiles Birds ( Eagles Pigeons Crows ) )";
//			List<String> questions = Arrays.asList(
//					"Reptiles: Why are many reptiles green?",
//					"Birds: How do birds fly?",
//					"Eagles: How endangered are eagles?",
//					"Pigeons: Where in the world are pigeons most densely populated?",
//					"Eagles: Where do most eagles live?");
//			List<String> queries = Arrays.asList(
//					"Eagles How en",
//					"Birds Where",
//					"Reptiles Why do",
//					"Animals Wh");
//			System.out.println(doOntology(flattenedTree, questions, queries));
		}
}
