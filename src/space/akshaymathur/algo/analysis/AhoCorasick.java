package space.akshaymathur.algo.analysis;

import java.util.List;
import java.util.Objects;
import java.lang.reflect.Array;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class AhoCorasick {
    private final Node root;

    public AhoCorasick(List<String> dictionary) {
        root = new Node(/* character= */ null, "");
        preprocessDictionary(dictionary);
    }

    private void preprocessDictionary(List<String> dictionary) {
        for (String word : dictionary) {
            addWord(word.toLowerCase());
        }
        setFailureLinks(root);
    }

    private void addWord(String word) {
        Node parent = root;
        for (char c : word.toCharArray()) {
            parent = parent.addChild(c);
        }
    }

    private void setFailureLinks(Node node) {
        if (node == root) {
            // self link
            node.setFailureLink(node);

        } else {
            for (CharBuffer suffix : getAllSuffixes(node.getStringTillHere().substring(1).toCharArray())) {
                Node failureLinkNode = findPrefixNodeInTree(suffix);
                if (failureLinkNode != null) {
                    node.setFailureLink(failureLinkNode);
                }
            }
            if (node.getFailureLink() == null) {
                node.setFailureLink(root);
            }
        }
        for (Node child : node.getChildren()) {
            setFailureLinks(child);
        }
    }

    Node findPrefixNodeInTree(CharBuffer str) {
        Node parent = root;
        for (int i = 0; i < str.length(); i++) {
            if (parent.containsChild(str.charAt(i))) {
                parent = parent.getChild(str.charAt(i));
            } else {
                return null;
            }
        }
        return parent;
    }

    private List<CharBuffer> getAllSuffixes(char[] str) {
        List<CharBuffer> suffixes = new ArrayList<>();
        int endIndex = str.length;
        for (int i = 0; i < endIndex; i++) {
            suffixes.add(CharBuffer.wrap(str, i, endIndex - i));
        }
        return suffixes;
    }

    private static class Node {
        private Character character;
        private Node failureLink;
        private Node[] children = new Node[26];
        private String stringTillHere;
        private boolean isLeaf;

        public boolean isLeaf() {
            return isLeaf;
        }

        public String getStringTillHere() {
            return stringTillHere;
        }

        public Node(Character character, String stringTillHere) {
            this.character = character;
            this.children = new Node[26];
            this.stringTillHere = stringTillHere;
            this.isLeaf = true;
        }

        public Node getFailureLink() {
            return failureLink;
        }

        public void setFailureLink(Node failureLink) {
            this.failureLink = failureLink;
        }

        public Node addChild(char a) {
            this.isLeaf = false;
            if (children[a - 'a'] == null) {
                children[a - 'a'] = new Node(a, this.stringTillHere + String.valueOf(a));
            }
            return children[a - 'a'];
        }

        public List<Node> getChildren() {
            List<Node> children = new ArrayList<Node>(Arrays.asList(this.children));
            children.removeIf(Objects::isNull);
            return children;
        }

        public Node getChild(char c) {
            return this.children[c - 'a'];
        }

        public boolean containsChild(char c) {
            return !Objects.isNull(this.children[c - 'a']);
        }

        public String toString() {
            if (character == null) {
                return "root";
            }
            return character.toString() + (isLeaf ? "(L) " : "") + " -- " + failureLink.toString();
        }
    }

    public void printTree() {
        printTree(root, 0);
        System.out.println();
    }

    private void printTree(Node node, int indent) {
        System.out.print("_".repeat(indent));
        System.out.print(node);
        System.out.println();
        System.out.println(" ".repeat(indent) + "|");
        for (Node child : node.getChildren()) {
            printTree(child, indent + 4);
        }
    }

    public List<String> findAllSubstrings(String s){
        Node parent = root;
        List<String> substrings = new ArrayList<>();
        int index = 0;
        char[] str = s.toCharArray();
        while(index < s.length()){
            char c = str[index];
            if(parent.isLeaf()){
                substrings.add(parent.getStringTillHere());
            }
            if(parent.containsChild(c)){
                parent = parent.getChild(c);
                index++;
            }else{
                parent = parent.getFailureLink();
            }
        }
        return substrings;
    }

    public static void main(String... args) {
        List<String> dictionary = List.of("acc", "atc", "cat", "gcg");
        AhoCorasick ahoCorasick = new AhoCorasick(dictionary);
        ahoCorasick.printTree();
        for(String subString:  ahoCorasick.findAllSubstrings("gcatcg"))
        {
            System.out.println(subString);
        }
    }
}