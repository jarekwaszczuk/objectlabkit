/**
 * 
 */
package net.objectlab.kit.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Benoit Xhenseval
 *
 */
public class ReadOnlyExpiringHashMap<K, V> extends AbstractReadOnlyExpiringCollection implements ReadOnlyExpiringMap<K, V> {
    private static final String COLLECTION_IS_IMMUTABLE = "Collection is immutable";
    private Map<K, V> delegate = new HashMap<K, V>();
    private final MapLoader<K, V> loader;

    public ReadOnlyExpiringHashMap(final ReadOnlyExpiringHashMapBuilder<K, V> builder) {
        loader = builder.getLoader();
        setId(builder.getId());
        setExpiryTimeoutMilliseconds(builder.getExpiryTimeoutMilliseconds());
        setReloadOnExpiry(builder.isReloadOnExpiry());
        setLoadOnFirstAccess(builder.isLoadOnFirstAccess());
        setReloadWhenExpired(builder.isReloadWhenExpired());
        setTimeProvider(builder.getTimeProvider());
        start();
    }

    @Override
    protected void doLoad() {
        final DefaultMapBuilder<K, V> builder = new DefaultMapBuilder<K, V>(getId());
        loader.load(builder);
        delegate = builder.build();
    }

    public void clear() {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    public boolean containsKey(final Object key) {
        validateOnAccess();
        return delegate.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        validateOnAccess();
        return delegate.containsValue(value);
    }

    public Set<java.util.Map.Entry<K, V>> entrySet() {
        validateOnAccess();
        return delegate.entrySet();
    }

    public V get(final Object key) {
        validateOnAccess();
        return delegate.get(key);
    }

    public boolean isEmpty() {
        validateOnAccess();
        return delegate.isEmpty();
    }

    public Set<K> keySet() {
        validateOnAccess();
        return delegate.keySet();
    }

    public V put(final K key, final V value) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    public void putAll(final Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    public V remove(final Object key) {
        throw new UnsupportedOperationException(COLLECTION_IS_IMMUTABLE);
    }

    public int size() {
        validateOnAccess();
        return delegate.size();
    }

    public Collection<V> values() {
        validateOnAccess();
        return delegate.values();
    }

    @Override
    protected void doClear() {
        delegate.clear();
    }

    public void reload() {
        doLoad();
    }
}
