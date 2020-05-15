package collection;

import java.util.HashSet;

public class HandlerId {
    private HashSet<Integer> usedId;
    private HashSet<Integer> unusedId;

    public HandlerId() {
        usedId = new HashSet<Integer>();
        unusedId = new HashSet<Integer>();
    }

    public Integer provideId() {
        Integer id;
        if(unusedId.isEmpty()) {
            id = usedId.size();
            usedId.add(usedId.size());
        }
        else {
            id = unusedId.iterator().next();
            unusedId.remove(id);
            usedId.add(id);
        }
        return id;
    }

    public boolean contains(Integer id) {
        return usedId.contains(id);
    }

    public void removeId(Integer id) {
        unusedId.add(id);
        usedId.remove(id);
    }

    public void clear() {
        usedId.clear();
        unusedId.clear();
    }
}
