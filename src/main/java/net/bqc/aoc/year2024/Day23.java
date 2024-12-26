package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 extends Solution<String> {

    private static class Node implements Comparable<Node> {
        String label;

        Node(String label) {
            this.label = label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(label, node.label);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(label);
        }

        @Override
        public int compareTo(Node o) {
            return this.label.compareTo(o.label);
        }
    }

    private static class Graph {
        Map<Node, Set<Node>> adjNodes = new HashMap<>();

        Node addNode(String label) {
            Node node = new Node(label);
            adjNodes.putIfAbsent(node, new HashSet<>());
            return node;
        }

        void addEdge(Node first, Node second) {
            adjNodes.get(first).add(second);
            adjNodes.get(second).add(first);
        }
    }

    @Override
    public String solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        Graph network = readNetwork(inputLines);

        Set<Set<Node>> connectedNodes = new HashSet<>();
        List<Node> fromNodes = isPart2() ? network.adjNodes.keySet().stream().toList() :
            network.adjNodes.keySet().stream().filter(n -> n.label.startsWith("t")).toList();
        fromNodes.forEach(n -> connectedNodes.addAll(findConnectedTripleNodes(network, n)));

        return !isPart2() ? String.valueOf(connectedNodes.size())
            : findLargestCluster(connectedNodes, network).stream().sorted().map(n -> n.label).collect(Collectors.joining(","));
    }

    private Set<Node> findLargestCluster(Set<Set<Node>> connectedNodes, Graph network) {
        // TODO: Should implement Bron–Kerbosch algorithm for correctness
        for (Set<Node> connectedCluster : connectedNodes) {
            for (Node node : network.adjNodes.keySet()) {
                if (connectedCluster.contains(node)) continue;
                boolean fullyConnected = true;
                for (Node clusterNode : connectedCluster) {
                    if (!network.adjNodes.get(node).contains(clusterNode)) {
                        fullyConnected = false;
                        break;
                    }
                }
                if (fullyConnected) connectedCluster.add(node);
            }
        }
        return connectedNodes.stream().max(Comparator.comparingInt(Set::size)).get();
    }

    private Set<Set<Node>> findConnectedTripleNodes(Graph network, Node node) {
        Set<Set<Node>> connectedNodes = new HashSet<>();
        List<Node> neighbours = new ArrayList<>(network.adjNodes.get(node));
        int n = neighbours.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (network.adjNodes.get(neighbours.get(i)).contains(neighbours.get(j))) {
                    connectedNodes.add(new HashSet<>(List.of(neighbours.get(i), neighbours.get(j), node)));
                }
            }
        }
        return connectedNodes;
    }

    private Graph readNetwork(List<String> inputLines) {
        Graph network = new Graph();
        inputLines.forEach(l -> {
            Node n1 = network.addNode(l.split("-")[0]);
            Node n2 = network.addNode(l.split("-")[1]);
            network.addEdge(n1, n2);
        });
        return network;
    }
}