
// LeetCode 341. Flatten Nested List Iterator
import java.util.*;
import java.util.Stack;
import java.util.NoSuchElementException;

/*
  Design a nested iterator to flatten a nested list of integers with the dynamicity
 * Time Complexity: O(1) where n is the total number of integers in the nested list.
 * Space Complexity: O(d) where d is depth of stack and O(1) is amortized space since nesting is sparse
*/
class DynamicNestedIterator implements Iterator<Integer> {
    Stack<Iterator<NestedInteger>> st;
    NestedInteger nextEl;

    public DynamicNestedIterator(List<NestedInteger> nestedList) {
        this.st = new Stack<Iterator<NestedInteger>>();
        this.st.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        return nextEl.getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!this.st.isEmpty()) {
            if (!this.st.peek().hasNext()) {
                this.st.pop();
            } else if ((nextEl = this.st.peek().next()).isInteger()) {
                return true;
            } else {
                this.st.push(nextEl.getList().iterator());
            }
        }

        return false;
    }
}

/*
 * DFS solution without the dynamicity
 * Time Complexity: O(n) where n is the total number of integers in the nested
 * list.
 * Space Complexity: O(n) for storing the flattened list.
 */

class NestedIterator implements Iterator<Integer> {
    List<Integer> result;
    int position = 0;

    public NestedIterator(List<NestedInteger> nestedList) {
        result = new ArrayList<>();
        dfs(nestedList);
    }

    public void dfs(List<NestedInteger> nestedList) {
        for (NestedInteger nested : nestedList) {
            if (nested.isInteger())
                result.add(nested.getInteger());
            else
                dfs(nested.getList());
        }
    }

    @Override
    public Integer next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return result.get(position++);
    }

    @Override
    public boolean hasNext() {
        return position < result.size();
    }
}

/**
 * Implementation of NestedInteger interface and NestedInteger class.
 */

interface NestedIntegerInterface {
    boolean isInteger();

    Integer getInteger();

    List<NestedInteger> getList();
}

class NestedInteger implements NestedIntegerInterface {
    NestedInteger nestedInteger;
    List<NestedInteger> nestedList;

    NestedInteger() {
        nestedList = new ArrayList<>();
    }

    public boolean isInteger() {
        return nestedList.isEmpty() && nestedInteger != null;
    }

    public Integer getInteger() {
        if (isInteger()) {
            return nestedInteger != null ? nestedInteger.getInteger() : null;
        }
        return null;
    }

    public List<NestedInteger> getList() {
        if (!isInteger()) {
            return nestedList;
        }
        return new ArrayList<>();
    }
}