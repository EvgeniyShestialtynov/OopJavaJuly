package ru.academits.shestialtynov.tree_main;

import ru.academits.shestialtynov.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();

        tree.add(8);
        tree.add(6);
        tree.add(4);
        tree.add(9);
        tree.add(2);
        tree.add(11);
        tree.add(4);
        tree.add(3);
        tree.add(13);
        tree.add(5);

        tree.breadthVisit();

        if (tree.remove(9)) {
            System.out.println("Удален узел со значением 9");
        } else {
            System.out.println("Дерево не содержит узел со значением 9");
        }

        if (tree.find(3)) {
            System.out.println("Дерево содержит узел со значением 3");
        } else {
            System.out.println("Дерево не содержит узел со значением 3");
        }

        tree.depthVisit();

        System.out.println();

        tree.depthVisitRecursive();
    }
}