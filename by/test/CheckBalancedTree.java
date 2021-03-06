package by.test;

import by.test.tree.Heights;
import by.test.tree.Leaf;
import by.test.tree.OrdinaryTreeImpl;

import java.util.*;

/**
 * todo: Nice: will be unit test and api functions
 * Created by Vasilina on 30.03.2015.
 */
public class CheckBalancedTree {
  public static void main(String[] args) {
    OrdinaryTreeImpl<Integer> ordinaryTree = new OrdinaryTreeImpl<Integer>();
    BalancedTreeImpl<Integer> balancedTree = new BalancedTreeImpl<Integer>();
    int[] arr = {1, 4, 9, 10, 11, 50, 55, 57, 60, 70, 77, 79};
    for (int value : arr) {
      balancedTree.addLeaf(value);
    }
    for (int value : arr) {
      ordinaryTree.addLeaf(value);
    }
    final Heights heights = new Heights();
    Leaf<Integer> rootLeaf = ordinaryTree.getRootLeaf();
    //Leaf<Integer> rootLeaf = balancedTree.getRootLeaf();
    Leaf currentLeaf = rootLeaf;
    int height = 1;
    Set<Leaf> visited = new HashSet<Leaf>();
    Deque<Leaf> queue = new ArrayDeque<Leaf>();
    queue.add(rootLeaf);
    while (balanced(heights, false) && queue.size() > 0) {
      Leaf lastInStack = queue.getLast();
      System.out.println(lastInStack.getValue());
      if (hasChildren(lastInStack) && !childrenVisited(lastInStack, visited)) {
        if (!visitedLeft(lastInStack, visited) && lastInStack.getLeft() != null) {
          queue.addLast(lastInStack.getLeft());
        } else if (!visitedRight(lastInStack, visited)) {
          queue.addLast(lastInStack.getRight());
        }
        height++;
      } else {
        //we found last leaf
        if (!hasChildren(lastInStack)) {
          updateHeights(heights, height);
        }
        queue.pollLast();
        height--;
        visited.add(lastInStack);
      }

    }
    //save height
    if (balanced(heights, true)) {
      System.out.println("Дерево сбалансировано");
    } else {
      System.out.println("Дерево несбалансировано");

    }

  }

  private static boolean childrenVisited(Leaf lastInStack, Set<Leaf> visited) {
    return visitedLeft(lastInStack, visited) && visitedRight(lastInStack, visited);
  }

  private static boolean visitedRight(Leaf lastInStack, Set<Leaf> visited) {
    if (lastInStack.getRight() == null) {
      return true;
    }
    return visited.contains(lastInStack.getRight());
  }

  private static boolean visitedLeft(Leaf lastInStack, Set<Leaf> visited) {
    if (lastInStack.getLeft() == null) {
      return true;
    }
    return visited.contains(lastInStack.getLeft());
  }

  private static boolean balanced(Heights heights, boolean allTreeScanned) {
    if (heights.getMaxHeight() == Integer.MIN_VALUE) {
      //empty tree or not analyzed still, initial value
      return true;
    }
    if (allTreeScanned) {
      if (heights.getMinHeight() == Integer.MAX_VALUE) {
        //tree all to the right or to the left
        heights.setMinHeight(0);
      }
    }
    if (allTreeScanned || (heights.getNumberOfLeafAnalyzed() >= 2 && heights.getMinHeight() > 0)) {
      if (heights.getMaxHeight() - heights.getMinHeight() > 1) {
        return false;
      }
    }
    return true;
  }

  private static void updateHeights(Heights heights, int height) {
    heights.setNumberOfLeafAnalyzed(heights.getNumberOfLeafAnalyzed()+1);
    if (heights.getMaxHeight() <= height) {
      heights.setMaxHeight(height);
    } else {
      if (heights.getMinHeight() > height) {
        heights.setMinHeight(height);
      }
    }
  }

  private static boolean hasChildren(Leaf currentLeaf) {
    return (currentLeaf.getLeft() != null) || (currentLeaf.getRight() != null);
  }

  public boolean checkBalance() {
    return false;
  }
}
