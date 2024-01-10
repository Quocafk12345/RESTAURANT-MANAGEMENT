
package com.edusys.dao;
import java.util.List;
public abstract class QLNhanVienDao<EntityType, KeyType>{
    public abstract void insert(EntityType entity);
    public abstract void update(EntityType entity);
    public abstract void delete(KeyType id);
    public abstract List<EntityType> selectAll();
     public abstract EntityType selectById(KeyType id);
     abstract protected List<EntityType> selectBySql(String sql, Object...args);
}
