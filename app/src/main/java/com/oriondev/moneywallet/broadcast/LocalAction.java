package com.oriondev.moneywallet.broadcast;


public class LocalAction {

    public static final String ACTION_BACKUP_SERVICE_STARTED = "LocalBroadCast::BackupServiceStarted";
    public static final String ACTION_BACKUP_SERVICE_RUNNING = "LocalBroadCast::BackupServiceRunning";
    public static final String ACTION_BACKUP_SERVICE_FINISHED = "LocalBroadCast::BackupServiceFinished";
    public static final String ACTION_BACKUP_SERVICE_FAILED = "LocalBroadCast::BackupServiceFailed";
    public static final String ACTION_BACKEND_SERVICE_STARTED = "LocalBroadCast::BackendServiceStarted";
    public static final String ACTION_BACKEND_SERVICE_RUNNING = "LocalBroadCast::BackendServiceRunning";
    public static final String ACTION_BACKEND_SERVICE_FINISHED = "LocalBroadCast::BackendServiceFinished";
    public static final String ACTION_BACKEND_SERVICE_FAILED = "LocalBroadCast::BackendServiceFailed";
    public static final String ACTION_EXCHANGE_RATES_UPDATED = "LocalBroadCast::ExchangeRatesUpdated";
    public static final String ACTION_ATTACHMENT_OP_STARTED = "LocalBroadCast::AttachmentOperationStarted";
    public static final String ACTION_ATTACHMENT_OP_FINISHED = "LocalBroadCast::AttachmentOperationFinished";
    public static final String ACTION_ATTACHMENT_OP_FAILED = "LocalBroadCast::AttachmentOperationFailed";
    public static final String ACTION_LEGACY_EDITION_UPGRADE_STARTED = "LocalBroadCast::LegacyEditionUpgradeStarted";
    public static final String ACTION_LEGACY_EDITION_UPGRADE_FINISHED = "LocalBroadCast::LegacyEditionUpgradeFinished";
    public static final String ACTION_LEGACY_EDITION_UPGRADE_FAILED = "LocalBroadCast::LegacyEditionUpgradeFailed";
    public static final String ACTION_IMPORT_SERVICE_STARTED = "LocalBroadCast::ImportServiceStarted";
    public static final String ACTION_IMPORT_SERVICE_FINISHED = "LocalBroadCast::ImportServiceFinished";
    public static final String ACTION_IMPORT_SERVICE_FAILED = "LocalBroadCast::ImportServiceFailed";
    public static final String ACTION_EXPORT_SERVICE_STARTED = "LocalBroadCast::ExportServiceStarted";
    public static final String ACTION_EXPORT_SERVICE_FINISHED = "LocalBroadCast::ExportServiceFinished";
    public static final String ACTION_EXPORT_SERVICE_FAILED = "LocalBroadCast::ExportServiceFailed";

    public static final String ACTION_ITEM_CLICK = "LocalBroadCast::ItemClicked";

    public static final String ACTION_CURRENT_WALLET_CHANGED = "LocalBroadCast::CurrentWalletChanged";
    public static final String ARGUMENT_WALLET_ID = "LocalBroadCast::WalletId";
}