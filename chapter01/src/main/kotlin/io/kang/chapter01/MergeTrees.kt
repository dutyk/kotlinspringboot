package io.kang.chapter01


class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Solution {
    fun mergeTrees(t1: TreeNode?, t2: TreeNode?): TreeNode? {
        if(t1 == null && t2 == null)
            return null

        val result = TreeNode(0)
        result.`val` = (t1?.`val`?: 0) + (t2?.`val`?: 0)
        result.left = mergeTrees(t1?.left, t2?.left)
        result.right = mergeTrees(t1?.right, t2?.right)
        return result
    }
}