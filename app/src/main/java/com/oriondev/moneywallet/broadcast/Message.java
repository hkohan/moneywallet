package com.oriondev.moneywallet.broadcast;


public class Message {

    public static final String ITEM_ID = "LocalBroadCast::Message::ItemId";
    public static final String ITEM_TYPE = "LocalBroadCast::Message::ItemType";

    public static final int TYPE_TRANSACTION = 1;
    public static final int TYPE_TRANSFER = 2;
    public static final int TYPE_CATEGORY = 3;
    public static final int TYPE_DEBT = 4;
    public static final int TYPE_BUDGET = 5;
    public static final int TYPE_SAVING = 6;
    public static final int TYPE_EVENT = 7;
    public static final int TYPE_RECURRENT_TRANSACTION = 8;
    public static final int TYPE_RECURRENT_TRANSFER = 9;
    public static final int TYPE_TRANSACTION_MODEL = 10;
    public static final int TYPE_TRANSFER_MODEL = 11;
}