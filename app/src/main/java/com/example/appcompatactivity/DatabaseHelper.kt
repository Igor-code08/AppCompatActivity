import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Класс для работы с базой данных
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping_cart.db" // Имя базы данных
        private const val DATABASE_VERSION = 1

        // Таблица и колонки
        const val TABLE_PRODUCTS = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_WEIGHT = "weight"
        const val COLUMN_PRICE = "price"

        private const val CREATE_TABLE_PRODUCTS = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_WEIGHT REAL,
                $COLUMN_PRICE REAL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PRODUCTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    // Добавление продукта в базу данных
    fun addProduct(name: String, weight: Double, price: Double): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_WEIGHT, weight)
            put(COLUMN_PRICE, price)
        }
        return db.insert(TABLE_PRODUCTS, null, values)
    }

    // Получение всех продуктов из базы данных
    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val weight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                products.add(Product(id, name, weight, price))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return products
    }
}

// Модель данных для продукта
data class Product(val id: Int, val name: String, val weight: Double, val price: Double)