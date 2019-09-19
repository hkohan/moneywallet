package com.oriondev.moneywallet.storage.wrapper;

import android.database.Cursor;

import com.oriondev.moneywallet.model.Group;
import com.oriondev.moneywallet.storage.database.Contract;
import com.oriondev.moneywallet.utils.DateUtils;

import java.util.Date;


public class TransferHeaderCursor extends AbstractHeaderCursor<DateRangeHeader> {

    public static final String COLUMN_ITEM_TYPE = "item_type";
    public static final String COLUMN_HEADER_START_DATE = "header_start_date";
    public static final String COLUMN_HEADER_END_DATE = "header_end_date";
    public static final String COLUMN_HEADER_GROUP_TYPE = "header_group_type";

    public final static int TYPE_HEADER = 0;
    public final static int TYPE_ITEM = 1;

    private static final int INDEX_ITEM_TYPE = 0;
    private static final int INDEX_HEADER_START_DATE = 1;
    private static final int INDEX_HEADER_END_DATE = 2;
    private static final int INDEX_HEADER_GROUP_TYPE = 3;

    private final Group mGroup;

    public TransferHeaderCursor(Cursor cursor, Group group) {
        super(cursor);
        mGroup = group;
        generateHeaders(cursor);
    }

    @Override
    protected void generateHeaders(Cursor cursor) {
        int indexTransactionDate = cursor.getColumnIndex(Contract.Transfer.DATE);
        if (cursor.moveToFirst()) {
            DateRangeHeader header = null;
            do {
                String dateTime = cursor.getString(indexTransactionDate);
                Date date = DateUtils.getDateFromSQLDateTimeString(dateTime);
                if (header == null) {
                    header = new DateRangeHeader(mGroup, null, null, date);
                    addHeader(header);
                } else {
                    if (!header.isInBounds(date)) {
                        header = new DateRangeHeader(mGroup, null, null, date);
                        addHeader(header);
                    }
                }
                addItem(cursor.getPosition());
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected String[] getHeaderColumnNames() {
        return new String[] {
                COLUMN_ITEM_TYPE,
                COLUMN_HEADER_START_DATE,
                COLUMN_HEADER_END_DATE,
                COLUMN_HEADER_GROUP_TYPE
        };
    }

    @Override
    protected String getHeaderString(int index) {
        if (isHeader()) {
            DateRangeHeader header = getHeader();
            switch (index) {
                case INDEX_HEADER_START_DATE:
                    return DateUtils.getSQLDateTimeString(header.getStartDate());
                case INDEX_HEADER_END_DATE:
                    return DateUtils.getSQLDateTimeString(header.getEndDate());
            }
        }
        return null;
    }

    @Override
    protected short getHeaderShort(int index) {
        return 0;
    }

    @Override
    protected int getHeaderInt(int index) {
        switch (index) {
            case INDEX_ITEM_TYPE:
                return isHeader() ? TYPE_HEADER : TYPE_ITEM;
            case INDEX_HEADER_GROUP_TYPE:
                return mGroup.getType();
            default:
                return 0;
        }
    }

    @Override
    protected long getHeaderLong(int index) {
        return 0;
    }

    @Override
    protected float getHeaderFloat(int index) {
        return 0;
    }

    @Override
    protected double getHeaderDouble(int index) {
        return 0;
    }

    @Override
    protected boolean isHeaderNull(int index) {
        return false;
    }
}
