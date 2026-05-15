package com.gramakhata.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gramakhata.app.data.model.Customer;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CustomerDao_Impl implements CustomerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Customer> __insertionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __deletionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __updateAdapterOfCustomer;

  public CustomerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomer = new EntityInsertionAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `customers` (`id`,`name`,`mobile`,`photoUri`,`shopName`,`totalCredit`,`totalPaid`,`createdAt`,`lastUpdated`,`isSettled`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getMobile());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        statement.bindString(5, entity.getShopName());
        statement.bindDouble(6, entity.getTotalCredit());
        statement.bindDouble(7, entity.getTotalPaid());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getLastUpdated());
        final int _tmp = entity.isSettled() ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
    this.__deletionAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `customers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `customers` SET `id` = ?,`name` = ?,`mobile` = ?,`photoUri` = ?,`shopName` = ?,`totalCredit` = ?,`totalPaid` = ?,`createdAt` = ?,`lastUpdated` = ?,`isSettled` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getMobile());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        statement.bindString(5, entity.getShopName());
        statement.bindDouble(6, entity.getTotalCredit());
        statement.bindDouble(7, entity.getTotalPaid());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getLastUpdated());
        final int _tmp = entity.isSettled() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Customer customer, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCustomer.insertAndReturnId(customer);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Customer customer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Customer customer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Customer>> getAllActiveCustomers() {
    final String _sql = "SELECT * FROM customers WHERE isSettled = 0 ORDER BY (totalCredit - totalPaid) DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<List<Customer>>() {
      @Override
      @Nullable
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfTotalCredit = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCredit");
          final int _cursorIndexOfTotalPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPaid");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "isSettled");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final double _tmpTotalCredit;
            _tmpTotalCredit = _cursor.getDouble(_cursorIndexOfTotalCredit);
            final double _tmpTotalPaid;
            _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            _item = new Customer(_tmpId,_tmpName,_tmpMobile,_tmpPhotoUri,_tmpShopName,_tmpTotalCredit,_tmpTotalPaid,_tmpCreatedAt,_tmpLastUpdated,_tmpIsSettled);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Customer>> getAllCustomers() {
    final String _sql = "SELECT * FROM customers ORDER BY (totalCredit - totalPaid) DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<List<Customer>>() {
      @Override
      @Nullable
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfTotalCredit = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCredit");
          final int _cursorIndexOfTotalPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPaid");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "isSettled");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final double _tmpTotalCredit;
            _tmpTotalCredit = _cursor.getDouble(_cursorIndexOfTotalCredit);
            final double _tmpTotalPaid;
            _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            _item = new Customer(_tmpId,_tmpName,_tmpMobile,_tmpPhotoUri,_tmpShopName,_tmpTotalCredit,_tmpTotalPaid,_tmpCreatedAt,_tmpLastUpdated,_tmpIsSettled);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Customer> getCustomerById(final long id) {
    final String _sql = "SELECT * FROM customers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<Customer>() {
      @Override
      @Nullable
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfTotalCredit = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCredit");
          final int _cursorIndexOfTotalPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPaid");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "isSettled");
          final Customer _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final double _tmpTotalCredit;
            _tmpTotalCredit = _cursor.getDouble(_cursorIndexOfTotalCredit);
            final double _tmpTotalPaid;
            _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            _result = new Customer(_tmpId,_tmpName,_tmpMobile,_tmpPhotoUri,_tmpShopName,_tmpTotalCredit,_tmpTotalPaid,_tmpCreatedAt,_tmpLastUpdated,_tmpIsSettled);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCustomerByIdSync(final long id,
      final Continuation<? super Customer> $completion) {
    final String _sql = "SELECT * FROM customers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Customer>() {
      @Override
      @Nullable
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfTotalCredit = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCredit");
          final int _cursorIndexOfTotalPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPaid");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "isSettled");
          final Customer _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final double _tmpTotalCredit;
            _tmpTotalCredit = _cursor.getDouble(_cursorIndexOfTotalCredit);
            final double _tmpTotalPaid;
            _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            _result = new Customer(_tmpId,_tmpName,_tmpMobile,_tmpPhotoUri,_tmpShopName,_tmpTotalCredit,_tmpTotalPaid,_tmpCreatedAt,_tmpLastUpdated,_tmpIsSettled);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Customer>> searchCustomers(final String query) {
    final String _sql = "SELECT * FROM customers WHERE name LIKE '%' || ? || '%' AND isSettled = 0 ORDER BY (totalCredit - totalPaid) DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<List<Customer>>() {
      @Override
      @Nullable
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfShopName = CursorUtil.getColumnIndexOrThrow(_cursor, "shopName");
          final int _cursorIndexOfTotalCredit = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCredit");
          final int _cursorIndexOfTotalPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPaid");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "isSettled");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpMobile;
            _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpShopName;
            _tmpShopName = _cursor.getString(_cursorIndexOfShopName);
            final double _tmpTotalCredit;
            _tmpTotalCredit = _cursor.getDouble(_cursorIndexOfTotalCredit);
            final double _tmpTotalPaid;
            _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            _item = new Customer(_tmpId,_tmpName,_tmpMobile,_tmpPhotoUri,_tmpShopName,_tmpTotalCredit,_tmpTotalPaid,_tmpCreatedAt,_tmpLastUpdated,_tmpIsSettled);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalDues() {
    final String _sql = "SELECT SUM(totalCredit - totalPaid) FROM customers WHERE isSettled = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getActiveDebtorCount() {
    final String _sql = "SELECT COUNT(*) FROM customers WHERE isSettled = 0 AND (totalCredit - totalPaid) > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"customers"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
