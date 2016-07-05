package io.github.declanw3.colourfall;

import java.util.ArrayList;

/**
 * A store of an int value. You can register a listener that will be notified
 * when the value changes.
 * http://stackoverflow.com/questions/9387000/android-execute-code-on-variable-change
 */
public class IntValueStore {

    /**
     * The current value.
     */
    int mValue;

    /**
     * The listener (you might want turn this into an array to support many
     * listeners)
     */
    private ArrayList<IntValueStoreListener> mListeners;

    private void init()
    {
        mListeners = new ArrayList<>();
    }

    /**
     * Construct a the int store.
     *
     * @param initialValue The initial value.
     */
    public IntValueStore(int initialValue) {
        mValue = initialValue;
        init();
    }

    /**
     * Sets a listener on the store. The listener will be modified when the
     * value changes.
     *
     * @param listener The {@link IntValueStoreListener}.
     */
    public void addListener(IntValueStoreListener listener) {
        mListeners.add(listener);
    }

    /**
     * Set a new int value.
     *
     * @param newValue The new value.
     */
    public void setValue(int newValue) {
        mValue = newValue;
        if (mListeners != null) {
            for(IntValueStoreListener listener: mListeners) {
                listener.onValueChanged(mValue);
            }
        }
    }

    /**
     * Get the current value.
     *
     * @return The current int value.
     */
    public int getValue() {
        return mValue;
    }

    /**
     * Callbacks by {@link IntValueModel}.
     */
    public static interface IntValueStoreListener {
        /**
         * Called when the value of the int changes.
         *
         * @param newValue The new value.
         */
        void onValueChanged(int newValue);
    }
}