package tachiyomi.data.manga.resolver

import android.content.ContentValues
import android.database.Cursor
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping
import com.pushtorefresh.storio3.sqlite.StorIOSQLite
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver
import com.pushtorefresh.storio3.sqlite.operations.put.DefaultPutResolver
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery
import com.pushtorefresh.storio3.sqlite.queries.InsertQuery
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery
import tachiyomi.domain.manga.Manga
import tachiyomi.data.manga.table.MangaTable.COL_ARTIST
import tachiyomi.data.manga.table.MangaTable.COL_AUTHOR
import tachiyomi.data.manga.table.MangaTable.COL_COVER
import tachiyomi.data.manga.table.MangaTable.COL_DESCRIPTION
import tachiyomi.data.manga.table.MangaTable.COL_FAVORITE
import tachiyomi.data.manga.table.MangaTable.COL_FLAGS
import tachiyomi.data.manga.table.MangaTable.COL_GENRE
import tachiyomi.data.manga.table.MangaTable.COL_ID
import tachiyomi.data.manga.table.MangaTable.COL_INITIALIZED
import tachiyomi.data.manga.table.MangaTable.COL_LAST_UPDATE
import tachiyomi.data.manga.table.MangaTable.COL_SOURCE
import tachiyomi.data.manga.table.MangaTable.COL_STATUS
import tachiyomi.data.manga.table.MangaTable.COL_TITLE
import tachiyomi.data.manga.table.MangaTable.COL_URL
import tachiyomi.data.manga.table.MangaTable.COL_VIEWER
import tachiyomi.data.manga.table.MangaTable.TABLE

internal class MangaTypeMapping : SQLiteTypeMapping<Manga>(
  MangaPutResolver(),
  MangaGetResolver(),
  MangaDeleteResolver()
)

internal class MangaPutResolver : DefaultPutResolver<Manga>() {

  override fun mapToInsertQuery(obj: Manga): InsertQuery {
    return InsertQuery.builder()
      .table(TABLE)
      .build()
  }

  override fun mapToUpdateQuery(obj: Manga): UpdateQuery {
    return UpdateQuery.builder()
      .table(TABLE)
      .where("$COL_ID = ?")
      .whereArgs(obj.id)
      .build()
  }

  override fun mapToContentValues(obj: Manga): ContentValues {
    return ContentValues(15).apply {
      put(COL_ID, obj.id)
      put(COL_SOURCE, obj.source)
      put(COL_URL, obj.url)
      put(COL_ARTIST, obj.artist)
      put(COL_AUTHOR, obj.author)
      put(COL_DESCRIPTION, obj.description)
      put(COL_GENRE, obj.genre)
      put(COL_TITLE, obj.title)
      put(COL_STATUS, obj.status)
      put(COL_COVER, obj.cover)
      put(COL_FAVORITE, obj.favorite)
      put(COL_LAST_UPDATE, obj.lastUpdate)
      put(COL_INITIALIZED, obj.initialized)
      put(COL_VIEWER, obj.viewer)
      put(COL_FLAGS, obj.flags)
    }
  }
}

internal class MangaGetResolver : DefaultGetResolver<Manga>() {
  override fun mapFromCursor(storIOSQLite: StorIOSQLite, cursor: Cursor): Manga {
    val id = cursor.getLong(cursor.getColumnIndex(COL_ID))
    val source = cursor.getLong(cursor.getColumnIndex(COL_SOURCE))
    val url = cursor.getString(cursor.getColumnIndex(COL_URL))
    val artist = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
    val author = cursor.getString(cursor.getColumnIndex(COL_AUTHOR))
    val description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
    val genre = cursor.getString(cursor.getColumnIndex(COL_GENRE))
    val title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
    val status = cursor.getInt(cursor.getColumnIndex(COL_STATUS))
    val cover = cursor.getString(cursor.getColumnIndex(COL_COVER))
    val favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1
    val lastUpdate = cursor.getLong(cursor.getColumnIndex(COL_LAST_UPDATE))
    val initialized = cursor.getInt(cursor.getColumnIndex(COL_INITIALIZED)) == 1
    val viewer = cursor.getInt(cursor.getColumnIndex(COL_VIEWER))
    val flags = cursor.getInt(cursor.getColumnIndex(COL_FLAGS))

    return Manga(id, source, url, artist, author, description, genre, title, status,
      cover, favorite, lastUpdate, initialized, viewer, flags)
  }
}

internal class MangaDeleteResolver : DefaultDeleteResolver<Manga>() {

  override fun mapToDeleteQuery(obj: Manga): DeleteQuery {
    return DeleteQuery.builder()
      .table(TABLE)
      .where("$COL_ID = ?")
      .whereArgs(obj.id)
      .build()
  }
}
