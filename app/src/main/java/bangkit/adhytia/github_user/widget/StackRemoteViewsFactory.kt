package bangkit.adhytia.github_user.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import bangkit.adhytia.github_user.helper.MappingHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private var cursor: Cursor? = null

    override fun onDataSetChanged() {
        cursor?.close()
        val identityToken = Binder.clearCallingIdentity()
        runBlocking {
            launch {
                cursor = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)
                val userList = MappingHelper.mapCursorToArrayList(cursor)
                mWidgetItems.addAll(MappingHelper.mapImageUrlInObjectToBitmap(userList, mContext))
            }
        }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            FavoriteGithubUserWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}