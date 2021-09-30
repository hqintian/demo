package com.example.demo;

import java.util.HashMap;
import java.util.Map;

class LFUCache {

    public static void main(String[] args) {
        //["LFUCache","put","get"]
        //[[0],[0,0],[0]]
        LFUCache lFUCache = new LFUCache(0);
        lFUCache.put(0, 0);
        lFUCache.get(0);


    }

    class Node {
        int key, value, freq;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            freq = 1;
        }
    }

    class FreqList {
        Node head = null;
        Node tail = null;

        public FreqList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public FreqList setFirst(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            return this;
        }

        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
        }
    }

    int minFreq = 1;
    int capacity = 0;
    Map<Integer, Node> map = new HashMap<>();
    Map<Integer, FreqList> freqMap = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node != null) {
            setMinFreq(node);
            freqMap.get(node.freq).remove(node);
            int newFreq = node.freq + 1;
            node.freq = newFreq;
            FreqList freqList = freqMap.get(newFreq);
            if (freqList != null) {
                freqMap.put(newFreq, freqList.setFirst(node));
            } else {
                freqMap.put(newFreq, new FreqList().setFirst(node));
            }
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }
        Node node = map.get(key);
        if (node != null) {
            setMinFreq(node);
            node.value = value;
            freqMap.get(node.freq).remove(node);
            int newFreq = node.freq + 1;
            node.freq = newFreq;
            FreqList freqList = freqMap.get(newFreq);
            if (freqList != null) {
                freqMap.put(newFreq, freqList.setFirst(node));
            } else {
                freqMap.put(newFreq, new FreqList().setFirst(node));
            }
        } else {
            node = new Node(key, value);
            if (map.size() == capacity) {
                FreqList freqList = freqMap.get(minFreq);
                map.remove(freqList.tail.prev.key);
                freqList.remove(freqList.tail.prev);
            }
            map.put(key, node);
            minFreq = 1;
            FreqList fList = freqMap.get(minFreq);
            if (fList != null) {
                freqMap.put(minFreq, fList.setFirst(node));
            } else {
                freqMap.put(minFreq, new FreqList().setFirst(node));
            }
        }
    }

    public void setMinFreq(Node node) {
        if (node.freq == minFreq
                && node.prev.prev == null && node.next.next == null) {
            minFreq = node.freq + 1;
        }
    }
}
