package ru.academits.shestialtynov.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Tree<E extends Comparable<E>> {
    private TreeNode<E> rootNode;
    private int size;

    public Tree() {
    }

    public void add(E data) {
        if (rootNode == null) {
            rootNode = new TreeNode<>(data);
            size++;

            return;
        }

        TreeNode<E> currentNode = rootNode;

        while (true) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new TreeNode<>(data));
                    size++;

                    return;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new TreeNode<>(data));
                    size++;

                    return;
                }
            }
        }
    }

    public boolean find(E data) {
        TreeNode<E> currentNode;

        for (currentNode = rootNode; data.compareTo(currentNode.getData()) != 0; ) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    return false;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private void nodeRemove(TreeNode<E> currentNodeParent, TreeNode<E> currentNode, TreeNode<E> currentNodeChild) {
        if (currentNodeParent != null) {
            if (currentNodeParent.getData().compareTo(currentNode.getData()) > 0) {
                currentNodeParent.setLeft(currentNodeChild);
            } else {
                currentNodeParent.setRight(currentNodeChild);
            }
        } else {
            rootNode = currentNodeChild;
        }
    }

    public boolean remove(E data) {
        TreeNode<E> currentNode;
        TreeNode<E> currentNodeParent = null;

        for (currentNode = rootNode; data.compareTo(currentNode.getData()) != 0; ) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNodeParent = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    return false;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNodeParent = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    return false;
                }
            }
        }

        if (currentNode.getLeft() == null) {
            nodeRemove(currentNodeParent, currentNode, currentNode.getRight());
        } else if (currentNode.getRight() == null) {
            nodeRemove(currentNodeParent, currentNode, currentNode.getLeft());
        } else {
            TreeNode<E> minRightChild = currentNode.getRight();
            TreeNode<E> minRightChildParent = currentNode;

            while (minRightChild.getLeft() != null) {
                minRightChildParent = minRightChild;
                minRightChild = minRightChild.getLeft();
            }

            nodeRemove(minRightChildParent, minRightChild, minRightChild.getRight());

            nodeRemove(currentNodeParent, currentNode, minRightChild);
        }
        size--;

        return true;
    }

    public int getSize() {
        return size;
    }

    public void breadthVisit() {
        Queue<TreeNode<E>> queue = new LinkedList<>();

        queue.add(rootNode);
        TreeNode<E> temporaryNode;

        while (!queue.isEmpty()) {
            temporaryNode = queue.remove();
            System.out.println(temporaryNode.getData());

            if (temporaryNode.getLeft() != null) {
                queue.add(temporaryNode.getLeft());
            }

            if (temporaryNode.getRight() != null) {
                queue.add(temporaryNode.getRight());
            }
        }
    }

    public void depthVisit() {
        Deque<TreeNode<E>> deque = new LinkedList<>();

        deque.addFirst(rootNode);
        TreeNode<E> temporaryNode;

        while (!deque.isEmpty()) {
            temporaryNode = deque.removeFirst();
            System.out.println(temporaryNode.getData());

            if (temporaryNode.getRight() != null) {
                deque.addFirst(temporaryNode.getRight());
            }

            if (temporaryNode.getLeft() != null) {
                deque.addFirst(temporaryNode.getLeft());
            }
        }
    }

    public void depthVisitRecursive() {
        recursionForDepthVisitRecursive(rootNode);
    }

    private void recursionForDepthVisitRecursive(TreeNode<E> node) {
        System.out.println(node.getData());

        if (node.getLeft() != null) {
            recursionForDepthVisitRecursive(node.getLeft());
        }

        if (node.getRight() != null) {
            recursionForDepthVisitRecursive(node.getRight());
        }
    }
}