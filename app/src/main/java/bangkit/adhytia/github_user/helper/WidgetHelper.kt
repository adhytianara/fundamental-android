package bangkit.adhytia.github_user.helper

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import bangkit.adhytia.github_user.widget.FavoriteGithubUserWidget
import bangkit.adhytia.github_user.R

object WidgetHelper {
    fun updateWidgetData(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(
                context,
                FavoriteGithubUserWidget::class.java
            )
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
    }
}