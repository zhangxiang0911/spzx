package com.zhangxiang.spzx.manager;

import java.util.*;

public class Lee {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row=0;
        while(matrix[row][0]<target&&row<matrix.length){
            row++;
        }
        row--;
        System.out.println(row);
        if(row==-1){
            return false;
        }
        int left=0, right=matrix[0].length-1;
        while(left<right){
            int mid=(left+right)/2;
            if(matrix[row][mid]==target){
                return true;
            }
            if(matrix[row][mid]>target){
                right=mid-1;
            }
            if(matrix[row][mid]<target){
                left=mid+1;
            }
        }
        return false;
    }

    public int[] searchRange(int[] nums, int target) {
        int left=0, right=nums.length-1;
        while(left<=right){
            int mid=(left+right)/2;
            if(nums[mid]==target){
                int first=mid, last=mid;
                while(first>0&&nums[first-1]==target){
                    first--;
                }
                while(last<nums.length-1&&nums[last+1]==target){
                    last++;
                }
                return new int[]{first, last};
            }
            if(nums[mid]>target){
                right=mid-1;
            }
            if(nums[mid]<target){
                left=mid+1;
            }
        }
        return new int[]{-1, -1};
    }

    public int search(int[] nums, int target) {
        int left, right;
        int index=0;
        for(int i=0;i<nums.length;i++){
            index=i;
            if(i==nums.length-1||nums[i]>nums[i+1]){
                break;
            }
        }
        if(nums[0]<=target){
            left=0;
            right=index;
        }else{
            left=index+1;
            right=nums.length-1;
        }
        while(left<=right){
            int mid=(left+right)/2;
            if(nums[mid]==target){
                return mid;
            }
            if(nums[mid]>target){
                right=mid-1;
            }
            if(nums[mid]<target){
                left=mid+1;
            }
        }
        return -1;
    }

    public int findMin(int[] nums) {
        int left=0, right=nums.length-1;
        while(left<right){
            int mid=(left+right)/2;
            if(nums[mid]>nums[right]){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        return nums[left];
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        if(n1 > n2)
            return findMedianSortedArrays(nums2, nums1);
        int cnt = (n1+n2+1)/2;
        int left = 0, right = n1;
        while(left < right) {
            int mid1 = (left+right)/2;
            int mid2 = cnt-mid1-1;
            if(nums1[mid1] >= nums2[mid2]){
                right = mid1;
            }else{
                left = mid1+1;
            }
        }
        int cnt1=left, cnt2=cnt-left;
        int leftVal = Math.max(cnt1==0?Integer.MIN_VALUE:nums1[cnt1-1] , cnt2==0?Integer.MIN_VALUE:nums2[cnt2-1]);
        if((n1+n2)%2==1){
            return leftVal;
        }
        int rightVal = Math.min(cnt1==n1?Integer.MAX_VALUE:nums1[cnt1] , cnt2==n2?Integer.MAX_VALUE:nums2[cnt2]);
        return (leftVal+rightVal)/2.0;
    }

    public boolean isValid(String s) {
        Stack<Character> stack =new Stack<>();
        if(s.length()%2==1||s.charAt(0)==')'||s.charAt(0)==']'||s.charAt(0)=='}'){
            return false;
        }
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch=='('||ch=='['||ch=='{'){
                stack.add(ch);
            }else{
                if(stack.size()==0){
                    return false;
                }
                char ch2=stack.pop();
                if((ch2=='['&&ch==']')||(ch2=='('&&ch==')')||(ch2=='{'&&ch=='}')){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map= new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<int[]> pq=new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]- o2[1];
            }
        });
        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
            if (pq.size()<k){
                pq.add(new int[]{entry.getKey(), entry.getValue()});
            }else {
                if (pq.peek()[1]<entry.getValue()){
                    pq.poll();
                    pq.add(new int[]{entry.getKey(), entry.getValue()});
                }
            }
        }
        int[] res=new int[k];
        for (int i = 0; i < k; i++) {
            res[i]=pq.peek()[0];
            pq.poll();
        }
        return res;
    }


    public int maxProfit(int[] prices) {
        int len = prices.length;
        int[][] res=new int[len][2];
        for (int i = 0; i < len; i++) {
            if (i-1==-1){
                res[i][0]=0;
                res[i][1]=-prices[i];
                continue;
            }
            res[i][0]=Math.max(res[i-1][0], res[i-1][1] + prices[i]);
            res[i][1]=Math.max(res[i-1][1], -prices[i]);
        }
        return res[len-1][0];
    }


    public static void main(String[] args) {
//        System.out.println(new Lee().searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 13));
//        System.out.println(Arrays.toString(new Lee().searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
//        System.out.println(new Lee().search(new int[]{1,3},3));
//        System.out.println(new Lee().findMin(new int[]{3,1,2}));
//        System.out.println(new Lee().findMedianSortedArrays(new int[]{1,2,4,5}, new int[]{1,3,4,7}));
        System.out.println(new Lee().isValid("()"));

    }

}
