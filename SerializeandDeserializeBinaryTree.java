package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Project Name : Leetcode
 * Package Name : leetcode
 * File Name : SerializeandDeserializeBinaryTree
 * Creator : Edward
 * Date : Aug, 2017
 * Description : TODO
 */
public class SerializeandDeserializeBinaryTree {

    /**
     * 297. Serialize and Deserialize Binary Tree
     *
     *
       1
      / \
     2   3
        / \
       4   5
     as "[1,2,3,null,null,4,5]"
     * time : O(n);
     * space : O(n);
     * @param root
     * @return
     */
    // Encodes a tree to a single string.
    
    solution 1: take advantage of feature of BFS
    /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    private final static char SP=',';
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb= new StringBuilder();
        if(root == null) return "";
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode curr = stack.pop();
            sb.append(curr.val).append(SP);
            if(curr.right!=null) stack.push(curr.right);
            if(curr.left !=null) stack.push(curr.left);
            
        }
        
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == null || data.length() == 0) return null;
        String array[] = data.split("\\" + SP);
        Queue<Integer> queue = new LinkedList<>();
        for(String val: array){
            queue.add(Integer.valueOf(val));
        }
        
        return deserializeHelper(queue);
    }
    
    public TreeNode deserializeHelper(Queue<Integer> queue){
        if(queue.isEmpty()) return null;
        Integer curr = queue.remove();
    
        Queue<Integer> smallerQueue = new LinkedList<>();
        while(!queue.isEmpty() && queue.peek() < curr){
            smallerQueue.add(queue.remove());
        }
        
        TreeNode root = new TreeNode(curr);
        root.left = deserializeHelper(smallerQueue);
        root.right = deserializeHelper(queue);
        
        return root;
        
    }
}

solution 2: implemented as  nomral bt 
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));    
        
    public String serialize(TreeNode root) {
       if (root == null) return "";
       StringBuilder res = new StringBuilder();
       Queue<TreeNode> queue = new LinkedList<>();
       queue.offer(root);
       while (!queue.isEmpty()) {
           TreeNode cur = queue.poll();
           if (cur == null) {
               res.append("null ");
               continue;
           }
           res.append(cur.val + " ");
           queue.offer(cur.left);
           queue.offer(cur.right);
       }
       return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == "") return null;
        String[] str = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(str[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        for (int i = 1; i < str.length; i++) {
            TreeNode cur = queue.poll();
            if (!str[i].equals("null")) {
                cur.left = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(cur.left);
            }
            if (!str[++i].equals("null")) {
                cur.right = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(cur.right);
            }
        }
        return root;
    }
}
