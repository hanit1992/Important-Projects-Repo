package oop.ex4.data_structures;
//class imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * a public class represent an avl tree. implements Iterable interface
 */
public class AvlTree implements Iterable<Integer> {

    //class constants

    /** The height of a leaf in the tree */
    private static final int LEAF_TREE_HEIGHT = 0;

    /** The initial number of nodes */
    private static final int INITIAL_NUMBER_OF_NODES_IN_THE_TREE = 0;

    /** The agreeable int that marks that the value wasn't found */
    private static final int INT_TO_MARK_UNFOUNDED_VALUE_INT_THE_TREE = -1;

    /** The height of a null child node in the tree */
    private static final int DEFAULT_NULL_NODE_TREE_HEIGHT = -1;

    /** The max absolute value of a valid balance, according to the avl algoritem  */
    private static final int MAX_VALID_BALANCE_VALUE_IN_ABS = 1;

    /** The negative possible invalid balance value */
    private static final int INVALID_BALANCE_NEGATIVE_VALUE = -2;

    /** The positive possible invalid balance value */
    private static final int INVALID_BALANCE_POSITIVE_VALUE = 2;

    /** the valid balance if the tree right son height is bigger than the left son height */
    private static final int OPTIONAL_VALID_BALANCE_FOR_BIGGER_RIGHT_SUB_TREE_HEIGHT = -1;

    /** the valid balance if the tree left son height is bigger the the right son height */
    private static final int OPTIONAL_VALID_BALANCE_FOR_BIGGER_LEFT_SUB_TREE_HEIGHT = 1;

    /** the valid balance if the tree sons height are the same */
    private static final int OPTIONAL_VALID_BALANCE_FOR_EQUAL_SUB_TREE_HEIGHTS = 0;

    /** The value that we will increase or decrease the height of some node in a relevant case */
    private static final int INT_FOR_CHANGING_NODE_HEIGHT = 1;

    /** The height of the root */
    private static final int ROOT_HEIGHT = 0;

    /** tht int to change the number of nodes in the tree */
    private static final int INT_FOR_CHANGING_THE_NUMBER_OF_NODES = 1;

    /** the int to initialize the h-1 height var */
    private static final int INITIAL_H_MINUS_ONE_NUMBER_OF_NODES = 1;

    /** the int to initialize the h-2 height var */
    private static final int INITIAL_H_MINUS_TWO_NUMBER_OF_NODES = 0;

    /** the int to initialize the h height var */
    private static final int INITIAL_MIN_NUMBER_OF_NODES = 1;



    //class data members

    /** The root of the tree */
    private TreeNode root;

    /** a data member for the method contains. */
    private int theFoundedNodeDepth = DEFAULT_NULL_NODE_TREE_HEIGHT;

    /** number of nodes counter, who is being updated by the add and the delete functions. */
    private int numberOfNodesInTheTree = INITIAL_NUMBER_OF_NODES_IN_THE_TREE;

    /** an arrayList of all the tree values, in order to use in the copy constructor */
    private ArrayList<Integer> listOfTheValuesOfTheTree = new ArrayList<>();

    /**
     * the Default class constructor
     */
    public AvlTree() {}

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     * The new tree contains all the values of the given tree, but not necessarily in the same structure.
     * @param tree an AvlTree class object to make a copy from
     */
    public AvlTree(AvlTree tree) {
        for (int element : tree.listOfTheValuesOfTheTree) {
            this.add(element);
        }
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data an int[] object of the given int values that needs to be added to the new tree
     */
    public AvlTree(int[] data) {
        if (data != null) {
            for (int item : data) {
                this.add(item);
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     * a nested static class which holds the tree nodes objects
     */
    private class TreeNode {

        //class constants

        /** a data member for the method contains. */
        private static final int INITIAL_BALANCE = 0;

        /** The height of a leaf in the tree */
        private static final int INITIAL_TREE_HEIGHT = 0;

        //class data members

        /** the key value of the node. in this case, the key is also the value itself  */
        private int key;

        /** the balance of the node. the balance os the height of the sub left tree minus
         * the height of the sub right tree */
        private int balance = INITIAL_BALANCE;

        /** the height of the tree that the node is his root */
        private int treeHeight = INITIAL_TREE_HEIGHT;

        /** the tree node left son */
        private TreeNode left;

        /** the tree node right son */
        private TreeNode right;

        /** the tree node parent  */
        private TreeNode parent;

        /** class constructor */
        private TreeNode(int key, TreeNode addParent) {
            this.key = key;
            this.parent = addParent;
        }
    }

    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue the given key
     * @return true if the node was added, false if it's already in the tree
     */
    public boolean add(int newValue) {

        //first checking if the root is null
        if (root == null) {
            root = new TreeNode(newValue, null);
            this.numberOfNodesInTheTree += INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
            // add the value to the values list
            listOfTheValuesOfTheTree.add(root.key);
            return true;
        }

        //else, calling the recursive add function. false is the default choice.
        TreeNode theNewAddedNode = recursiveAdd(root, null, newValue, false);
        //if we found that the node is already in their, return
        if (theNewAddedNode == null){
            return false;
        }
        //in other case, the node was added, and we add the value to the values list.
        this.listOfTheValuesOfTheTree.add(theNewAddedNode.key);

        //updating the tree after adding a new value
        updatingTheTree(theNewAddedNode);
        //updating the number of nodes in the list
        this.numberOfNodesInTheTree += INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
        return true;
    }

    /**
     * updating the tree. being called in each change of the tree, by adding valued or deleting
     * @param currentNode the initial node that we will update the tree from
     */
    private void updatingTheTree(TreeNode currentNode) {
        TreeNode theNodeToUpdate = currentNode;
        //entering a loop on all the nodes who are the parent of the initial one
        while (theNodeToUpdate != null) {
            if (theNodeToUpdate.parent==null){
                this.root = theNodeToUpdate;
            }
            if (theNodeToUpdate.left != null && theNodeToUpdate.right != null) {
                theNodeToUpdate.treeHeight = Math.max(theNodeToUpdate.left.treeHeight,
                        theNodeToUpdate.right.treeHeight) + INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
                updateBalance(theNodeToUpdate, theNodeToUpdate.left.treeHeight, theNodeToUpdate.right.treeHeight);
            }

            if (theNodeToUpdate.left == null && theNodeToUpdate.right != null) {
                theNodeToUpdate.treeHeight = theNodeToUpdate.right.treeHeight + INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
                updateBalance(theNodeToUpdate,DEFAULT_NULL_NODE_TREE_HEIGHT, theNodeToUpdate.right.treeHeight);
            }

            if (theNodeToUpdate.left != null && theNodeToUpdate.right == null) {
                theNodeToUpdate.treeHeight = theNodeToUpdate.left.treeHeight + INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
                updateBalance(theNodeToUpdate, theNodeToUpdate.left.treeHeight, DEFAULT_NULL_NODE_TREE_HEIGHT);
            }
            if (Math.abs(theNodeToUpdate.balance) > MAX_VALID_BALANCE_VALUE_IN_ABS) {
                this.reservingAvlProperty(theNodeToUpdate);
            }
            //changing the variable to the parent of the node to activate the update on it for the next loop
            theNodeToUpdate = theNodeToUpdate.parent;
        }
    }

    /**
     * a private method to add nodes to the tree as a recursive BST add algorithm
     *
     * @param node              the current node that we point to. if we reached null we will add a new node
     * @param currentNodeParent the parent of the current node
     * @param newValue          the value who needs to be added
     * @param isRight           is the node is the right son of the parent
     * @return the new node
     */
    private TreeNode recursiveAdd(TreeNode node, TreeNode currentNodeParent, int newValue, boolean isRight) {
        //reached a null node, and new node can be placed here.
        if (node == null) {
            TreeNode newNode = new TreeNode(newValue, currentNodeParent);
            if (isRight) {
                currentNodeParent.right = newNode;
            } else {
                currentNodeParent.left = newNode;
            }
            //return the new added node
            return newNode;
        }
        //the node is already in the tree
        if (node.key==newValue){
            //marking that the node was found by null
            return null;
        }
        //the node is not null. reaching the recursive call
        else if (node.key < newValue) {
            return recursiveAdd(node.right, node, newValue, true);
        } else if (node.key > newValue) {
            return recursiveAdd(node.left, node, newValue, false);
        }
        //we shouldn't get here
        return null;
    }

    /**
     * a private method to reserve the property of the class avl tree
     *
     * @param nodeToFix the node that his balance isn't valid by the definition of the avl tree
     */
    private void reservingAvlProperty(TreeNode nodeToFix) {

        if (nodeToFix.balance == INVALID_BALANCE_POSITIVE_VALUE) {
            //case LL rotation
            if (nodeToFix.left.balance == OPTIONAL_VALID_BALANCE_FOR_EQUAL_SUB_TREE_HEIGHTS ||
                    nodeToFix.left.balance == OPTIONAL_VALID_BALANCE_FOR_BIGGER_LEFT_SUB_TREE_HEIGHT) {
                rotateRight(nodeToFix);
            }
            //case LR rotation

            else if (nodeToFix.left.balance == OPTIONAL_VALID_BALANCE_FOR_BIGGER_RIGHT_SUB_TREE_HEIGHT) {
                LRrotation(nodeToFix);
            }
        }

        if (nodeToFix.balance == INVALID_BALANCE_NEGATIVE_VALUE) {
            //case RR rotation
            if (nodeToFix.right.balance == OPTIONAL_VALID_BALANCE_FOR_EQUAL_SUB_TREE_HEIGHTS ||
                    nodeToFix.right.balance == OPTIONAL_VALID_BALANCE_FOR_BIGGER_RIGHT_SUB_TREE_HEIGHT) {
                rotateLeft(nodeToFix);
            }
            // case RL rotation
            else if (nodeToFix.right.balance == OPTIONAL_VALID_BALANCE_FOR_BIGGER_LEFT_SUB_TREE_HEIGHT) {
                RLrotation(nodeToFix);
            }
        }
    }

    /**
     * updating the balance by the current sub tree heights
     * @param currentNode the node to change his balance
     * @param leftSonHeight the height of the node's left son
     * @param rightSonHeight the height of the node's right son
     */
    private void updateBalance(TreeNode currentNode, int leftSonHeight, int rightSonHeight) {
        currentNode.balance = leftSonHeight - rightSonHeight;
    }

    /**
     * rotate right the tree on a node that his balance has been broken, by changing the places of some nodes in the
     * tree, by the rotation AvlTree algoritem
     *
     * @param nodeToFix the invalid node
     * @return the new node that would be the son of the original node, for the LR and RL rotations
     */
    private TreeNode rotateRight(TreeNode nodeToFix) {

        //creating a copy of a node to reserve it
        TreeNode nodeCopy = nodeToFix.left;
        if (nodeCopy.right != null){
            nodeToFix.left = nodeCopy.right;
            nodeCopy.right.parent = nodeToFix;
        }
        else{
            nodeToFix.left = null;
        }
        nodeCopy.right = nodeToFix;
        if (nodeToFix.parent!=null){
            if (nodeToFix.parent.right == nodeToFix){
                nodeToFix.parent.right = nodeCopy;
            }
            else if (nodeToFix.parent.left==nodeToFix){
                nodeToFix.parent.left = nodeCopy;
            }
        }
        nodeCopy.parent = nodeToFix.parent;
        nodeToFix.parent = nodeCopy;

        //updating height

        //updating the main node height and balance
        if (nodeToFix.right != null && nodeToFix.left != null) {
            nodeToFix.treeHeight = Math.max(nodeToFix.left.treeHeight, nodeToFix.right.treeHeight)
                    + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeToFix, nodeToFix.left.treeHeight, nodeToFix.right.treeHeight);
        } else if (nodeToFix.left != null){
            nodeToFix.treeHeight = nodeToFix.left.treeHeight + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeToFix, nodeToFix.left.treeHeight, DEFAULT_NULL_NODE_TREE_HEIGHT);
        }
        else {nodeToFix.treeHeight = LEAF_TREE_HEIGHT;}

        //updating the copy height and balance
        if (nodeCopy.left != null && nodeCopy.right !=null) {
            nodeCopy.treeHeight = Math.max(nodeCopy.left.treeHeight, nodeToFix.treeHeight)
                    + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeCopy, nodeCopy.left.treeHeight, nodeCopy.right.treeHeight);
        } else if (nodeCopy.right != null){
            nodeCopy.treeHeight = nodeToFix.treeHeight + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeCopy, DEFAULT_NULL_NODE_TREE_HEIGHT, nodeCopy.right.treeHeight);
        }
        else {nodeCopy.treeHeight = LEAF_TREE_HEIGHT;}

        return nodeCopy;
    }

    /**
     * rotate left the tree on a node that his balance has been broken, by changing the places of some nodes in the
     * tree, by the rotation AvlTree algoritem
     * @param nodeToFix the invalid node
     * @return the new node that would be the son of the original node, for the LR and RL rotations
     */
    private TreeNode rotateLeft(TreeNode nodeToFix) {

        //creating a copy of a node to reserve it
        TreeNode nodeCopy = nodeToFix.right;

        if (nodeCopy.left != null)
            nodeToFix.right = nodeCopy.left;
        else {nodeToFix.right = null;}

        nodeCopy.left = nodeToFix;
        if (nodeToFix.parent!=null){
            if (nodeToFix.parent.right == nodeToFix){
                nodeToFix.parent.right = nodeCopy;
            }
            else if (nodeToFix.parent.left==nodeToFix){
                nodeToFix.parent.left = nodeCopy;
            }
        }

        nodeCopy.parent = nodeToFix.parent;
        nodeToFix.parent = nodeCopy;

        //updating height

        //updating the main node height and balance
        if (nodeToFix.left != null && nodeToFix.right != null) {
            nodeToFix.treeHeight = Math.max(nodeToFix.left.treeHeight, nodeToFix.right.treeHeight) +
                    INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeToFix, nodeToFix.left.treeHeight, nodeToFix.right.treeHeight);
        } else if (nodeToFix.right != null){
            nodeToFix.treeHeight = nodeToFix.right.treeHeight + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeToFix, DEFAULT_NULL_NODE_TREE_HEIGHT, nodeToFix.right.treeHeight);
        }
        else {nodeToFix.treeHeight = LEAF_TREE_HEIGHT;}

        //updating the copy height and balance
        if (nodeCopy.right != null && nodeCopy.left !=null) {
            nodeCopy.treeHeight = Math.max(nodeCopy.right.treeHeight, nodeToFix.treeHeight)
                    + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeCopy, nodeCopy.left.treeHeight, nodeCopy.right.treeHeight);
        } else if (nodeCopy.left != null){
            nodeCopy.treeHeight = nodeToFix.treeHeight + INT_FOR_CHANGING_NODE_HEIGHT;
            updateBalance(nodeCopy, nodeCopy.left.treeHeight, DEFAULT_NULL_NODE_TREE_HEIGHT);
        }
        else {nodeCopy.treeHeight = LEAF_TREE_HEIGHT;}

        return nodeCopy;
    }

    /**
     * taking care of the LR rotation case, by calling both the rotations functions in the relevant way
     * @param nodeToFix the node that his balance needs to be fixed
     */
    private void LRrotation(TreeNode nodeToFix) {
        //using the left rotation for the LL case to the LR case
        nodeToFix.left = rotateLeft(nodeToFix.left);
        rotateRight(nodeToFix);
    }

    /**
     * taking care of the RL rotation case, by calling both the rotations functions in the relevant way
     * @param nodeToFix the node that his balance needs to be fixed
     */
    private void RLrotation(TreeNode nodeToFix) {
        //using the right rotation for RR case for the RL rotation
        nodeToFix.right = rotateRight(nodeToFix.right);
        rotateLeft(nodeToFix);
    }

    /**
     * check whether the tree contains the given input value
     * @param searchVal the value that we want to check if the tree contains
     * @return the depth of the node (0 for the root) with the given value if it was found in, -1 otherwise
     */
    public int contains(int searchVal) {
        //calling the recursive function to find the node
        if (recursiveSearchForAGivenValue(searchVal, root, ROOT_HEIGHT) != null)
            return this.theFoundedNodeDepth;
        else {
            return INT_TO_MARK_UNFOUNDED_VALUE_INT_THE_TREE;
        }
    }

    /**
     * searching for a value in recursion, for the contains method to use
     * @param searchVal the value that we want to find
     * @param currentNode the node from which we will search in the recursion
     * @param depth the depth of the founded node
     * @return the founded node with the wanted value
     */
    private TreeNode recursiveSearchForAGivenValue(int searchVal, TreeNode currentNode, int depth) {
        //checking if we reached the value.
        if (currentNode == null)
            //value is not in the tree
            return null;
        if (currentNode.key == searchVal) {
            //the value was found, updating the node depth in the specific data member for this function
            this.theFoundedNodeDepth = depth;
            return currentNode;
            //if we didn't find it yet and it's not null, return a recursive call
        } else {
            if (searchVal > currentNode.key) {
                return recursiveSearchForAGivenValue(searchVal, currentNode.right, depth + 1);
            }

            if (searchVal < currentNode.key) {
                return recursiveSearchForAGivenValue(searchVal, currentNode.left, depth + 1);
            }
        }
        //shouldn't get here
        return null;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value that needs to be deleted
     * @return true if the value was found and the node was deleted, false otherwise
     */
    public boolean delete(int toDelete) {
        //marks if the node is the right son of the parent
        boolean isTheNodeTheRightSon = false;
        //calling the recursive function to search the node, in order to get the node if it was found
        TreeNode theNodeToDelete = recursiveSearchForAGivenValue(toDelete, root, 0);

        //reached null and didn't find it
        if (theNodeToDelete == null)
            return false;

        //checking the node's position regarding the parent
        if (theNodeToDelete.parent != null) {
            if (theNodeToDelete.parent.right == theNodeToDelete)
                //marking the local variable
                isTheNodeTheRightSon = true;
        }

        //deleting the node and getting the relevant node to update, in order to fix the tree in case of a violation
        TreeNode nodeToUpdate = recursiveDeleteByCases(theNodeToDelete, isTheNodeTheRightSon);
        //removing the node from the list of values
        listOfTheValuesOfTheTree.remove((Integer) theNodeToDelete.key);
        //if we did deleted some value, updating the tree by the relevant value
        if (nodeToUpdate != null) {
            updatingTheTree(nodeToUpdate);
        }
        //updating the number of nodes in the tree
        this.numberOfNodesInTheTree -= INT_FOR_CHANGING_THE_NUMBER_OF_NODES;
        return true;
    }

    /**
     * deleting the given node according to the relevant case
     * @param theNodeToDelete the TreeNode object that needs to be deleted
     * @param isTheNodeTheRightSon marks the position of the node compare to the parent
     * @return the node that we need to update from, according to the delete case
     */
    private TreeNode recursiveDeleteByCases(TreeNode theNodeToDelete, boolean isTheNodeTheRightSon) {

        //in case the node to delete has no sons
        if (theNodeToDelete.left == null && theNodeToDelete.right == null) {
            //in case it's the root
            if (theNodeToDelete.parent == null) {
                this.root = null;
                //returning null tree
                return null;
            }
            if (isTheNodeTheRightSon) {
                theNodeToDelete.parent.right = null;
            } else {
                theNodeToDelete.parent.left = null;
            }
            return theNodeToDelete.parent;
        }

        //in case the node to delete has one son

        if (theNodeToDelete.left == null) {
            if (theNodeToDelete.parent == null) {
                //in case it's the root
                theNodeToDelete.right.parent = null;
                this.root = theNodeToDelete.right;
                return root;
            } else if (isTheNodeTheRightSon) {
                theNodeToDelete.parent.right = theNodeToDelete.right;
            } else {
                theNodeToDelete.parent.left = theNodeToDelete.right;
            }
            return theNodeToDelete.right;

        } else if (theNodeToDelete.right == null) {
            //in case it's the root
            if (theNodeToDelete.parent == null) {
                theNodeToDelete.left.parent = null;
                this.root = theNodeToDelete.left;
                return root;
            }
            if (isTheNodeTheRightSon) {
                theNodeToDelete.parent.right = theNodeToDelete.left;
            } else {
                theNodeToDelete.parent.left = theNodeToDelete.left;
            }
            return theNodeToDelete.left;
        }

        //in case the node to delete has two sons
        else {
            //finding the smallest node of the right subtree to replace the node to delete
            TreeNode theSmallestRightSubTreeNode = theNodeToDelete.right;
            while (theSmallestRightSubTreeNode.left != null) {
                theSmallestRightSubTreeNode = theSmallestRightSubTreeNode.left;
            }
            TreeNode theNodeToReplaceWith = theSmallestRightSubTreeNode.parent;
            //switching the keys
            theNodeToDelete.key = theNodeToReplaceWith.key;
            //erasing the replacing node by the recursive call. the node is not the right son in any case.
            recursiveDeleteByCases(theNodeToReplaceWith, false);
        }
        //we shouldn't get here
        return null;
    }

    /**
     * @return the number of elements in the tree.
     */
    public int size() {
        return this.numberOfNodesInTheTree;
    }

    /**
     * returning an iterator that goes over the avl tree values in an ascending order, and doe's not implement the
     * remove Iterator class method
     * @return an iterator object the iterates over an avl tree
     */
    public Iterator<Integer> iterator() {

        /**
         * a nested local class implementing the Iterator<Integer> interface
         */
        class AvlTreeIterator implements Iterator<Integer> {

            //class constants

            /** The initial iteration number for the counter to start with */
            private static final int INITIAL_ITERATION_INT = 0;

            /** The int value for increasing the counter each time */
            private static final int INCREASING_ITERATION_COUNT = 1;

            //class data members

            /** counter of the iterations, holding the current iteration number for each time */
            private int currentIterationNum = INITIAL_ITERATION_INT;

            /** The current node of the tree that we point at, for the iteration order.
             * the initial node is the root */
            private TreeNode theCurrentNode = root;

            /** a mark field for the next recursive helper function */
            private boolean needToReturn = true;

            /** a mark field for the next recursive helper function */
            private boolean needToGoLeft = true;

            /**
             * iterates over the tree and returning the next value by an ascending order
             * throws an exception when there are no more values to iterate on
             * @return the next int in the iteration
             */
            public Integer next()  {
                if (hasNext()) {
                    currentIterationNum+=INCREASING_ITERATION_COUNT;
                    return nextRecursiveHelper();
                }
                throw new NoSuchElementException();
            }

            /**
             * the recursive helper method for the next() method. recursively iterates over the tree and retrning
             * the next value each time
             * @return the next int that needs to be returned
             */
            private int nextRecursiveHelper(){
                //in case we want to return a value
                if (needToReturn){
                    TreeNode localNode = theCurrentNode;
                    //if we haven't go left yet
                    if(needToGoLeft){
                        //reaching the leftest value
                        while (localNode.left!=null){
                            localNode = localNode.left;
                        }
                    }
                    //marking no return value for next time
                    needToReturn = false;
                    //changing the data member to be the founded next value
                    theCurrentNode = localNode;
                    //returning it
                    return theCurrentNode.key;
                }

                //in case we don't want to return yet

                //if there is a right son
                if (theCurrentNode.right !=null){
                    //first marking the return and the left signs
                    needToReturn = true;
                    needToGoLeft = true;
                    //updating the data member for the current founded node
                    theCurrentNode = theCurrentNode.right;
                    //recursive call
                    return nextRecursiveHelper();
                }
                //if there isn't a right son
                else {
                    //first marking the return and un marking the left signs
                    needToReturn = true;
                    needToGoLeft = false;
                    //updating the data member for the current founded node
                    theCurrentNode = theCurrentNode.parent;
                    //recursive call
                    return nextRecursiveHelper();
                }
            }

            /**
             * checking if the iterated array has another value to return
             * @return true if a next value exists, false otherwise
             */
            public boolean hasNext() {
                if (currentIterationNum == size()){
                    return false;
                }
                return true;
            }

            /**
             * not implementing this method of the interface. throwing exception object in the case of activating it
             */
            public void remove(){
                throw new UnsupportedOperationException();
            }
        }
        //the function returns a new AvlIterator class object, which is also an Iterator<Integer> class object
        return new AvlTreeIterator();
    }

    public static int findMinNodes(int h){
        //defining three variables which will represent the number of nodes for h-1, h-2 and the current min nodes for
        //h in the specific iteration
        int theMinNodesForTreeHMinusOne = INITIAL_H_MINUS_ONE_NUMBER_OF_NODES;
        int theMinNodesForTreeHMinusTwo = INITIAL_H_MINUS_TWO_NUMBER_OF_NODES;
        int theCurrentNumberOfMinNodes = INITIAL_MIN_NUMBER_OF_NODES;
        //entering a loop to count and calculate the min nodes for each height up to the given height.
        for (int i = 0; i<h;i++){
            theCurrentNumberOfMinNodes = theMinNodesForTreeHMinusOne + theMinNodesForTreeHMinusTwo +1;
            theMinNodesForTreeHMinusTwo = theMinNodesForTreeHMinusOne;
            theMinNodesForTreeHMinusOne = theCurrentNumberOfMinNodes;
        }
        return theCurrentNumberOfMinNodes;


    }
}