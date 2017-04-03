package com.twitterclient.databinding;
import com.twitterclient.R;
import com.twitterclient.BR;
import android.view.View;
public class ComposeFragBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivClose, 1);
        sViewsWithIds.put(R.id.ivUser, 2);
        sViewsWithIds.put(R.id.etTweet, 3);
        sViewsWithIds.put(R.id.sepTweet, 4);
        sViewsWithIds.put(R.id.btnTweet, 5);
        sViewsWithIds.put(R.id.tvLetterCount, 6);
        sViewsWithIds.put(R.id.btnDraft, 7);
    }
    // views
    public final android.widget.Button btnDraft;
    public final android.widget.Button btnTweet;
    public final android.widget.EditText etTweet;
    public final android.widget.ImageView ivClose;
    public final android.widget.ImageView ivUser;
    private final android.widget.RelativeLayout mboundView0;
    public final android.view.View sepTweet;
    public final android.widget.TextView tvLetterCount;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ComposeFragBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.btnDraft = (android.widget.Button) bindings[7];
        this.btnTweet = (android.widget.Button) bindings[5];
        this.etTweet = (android.widget.EditText) bindings[3];
        this.ivClose = (android.widget.ImageView) bindings[1];
        this.ivUser = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.sepTweet = (android.view.View) bindings[4];
        this.tvLetterCount = (android.widget.TextView) bindings[6];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ComposeFragBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ComposeFragBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ComposeFragBinding>inflate(inflater, com.twitterclient.R.layout.compose_frag, root, attachToRoot, bindingComponent);
    }
    public static ComposeFragBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ComposeFragBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.twitterclient.R.layout.compose_frag, null, false), bindingComponent);
    }
    public static ComposeFragBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ComposeFragBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/compose_frag_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ComposeFragBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}