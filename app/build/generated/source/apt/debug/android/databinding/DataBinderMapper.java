
package android.databinding;
import com.twitterclient.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 25;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.twitterclient.R.layout.frag_tweet_detail:
                    return com.twitterclient.databinding.FragTweetDetailBinding.bind(view, bindingComponent);
                case com.twitterclient.R.layout.compose_frag:
                    return com.twitterclient.databinding.ComposeFragBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -161775272: {
                if(tag.equals("layout/frag_tweet_detail_0")) {
                    return com.twitterclient.R.layout.frag_tweet_detail;
                }
                break;
            }
            case 525073909: {
                if(tag.equals("layout/compose_frag_0")) {
                    return com.twitterclient.R.layout.compose_frag;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}