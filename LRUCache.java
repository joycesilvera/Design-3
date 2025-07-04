import java.util.*;

// Time complexity: O(1)
// Space complexity: O(n)

//Intuition: We are using a hashmap to store the key-value pairs and a doubly linked list to maintain the order of Least Recently Used (LRU) items
public class LRUCache {
    final Node head = new Node();
    final Node tail = new Node();

    HashMap<Integer, Node> node_map;
    int cache_capacity;

    public LRUCache(int capacity) {
        head.next = tail;
        tail.prev = head;
        node_map = new HashMap(capacity);
        cache_capacity = capacity;
    }

    // Time complexity: O(1)
    public int get(int key) {
        int result = -1;
        Node node = new Node();
        node = node_map.get(key);
        if (node != null) {
            result = node.val;
            remove(node);
            add(node);
        }
        return result;
    }

    // Time complexity: O(1)
    public void put(int key, int value) {
        Node node = node_map.get(key);
        if (node != null) {
            remove(node);
            node.val = value;
            add(node);
        } else {
            if (node_map.size() == cache_capacity) {
                node_map.remove(tail.prev.key);
                remove(tail.prev);
            }

            Node new_node = new Node();
            new_node.key = key;
            new_node.val = value;

            node_map.put(key, new_node);
            add(new_node);
        }
    }

    public void add(Node node) {
        Node head_next = head.next;
        node.prev = head;
        head.next = node;
        node.next = head_next;
        head_next.prev = node;
    }

    public void remove(Node node) {
        Node prev_node = node.prev;
        Node next_node = node.next;

        prev_node.next = next_node;
        next_node.prev = prev_node;
    }

    class Node {
        int key;
        int val;
        Node next;
        Node prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
