//package dbunit_sample;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.Date;
//
//import org.dbunit.database.IDatabaseConnection;
//import org.dbunit.database.QueryDataSet;
//import org.dbunit.dataset.Column;
//import org.dbunit.dataset.DataSetException;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.ITable;
//import org.dbunit.dataset.ReplacementDataSet;
//import org.dbunit.dataset.excel.XlsDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.dbunit.operation.DatabaseOperation;
//
//import junit.framework.TestCase;
//
///**
// * DAOテストの基底クラス.
// */
//public abstract class DaoBaseTestCase extends TestCase {
//
//    /**
//     * 全テーブル名.
//     */
//    protected static final String[] TABLE_NAME_ALL;
//
//    /**
//     * テーブル数.
//     */
//    private static final int TABLE_SIZE = 1;
//
//    /**
//     * テーブル名.
//     */
//    private static final String TABLE_NAME = "syohin";
//
//    /**
//     * 元データ退避用一時ファイル.
//     */
//    private File file;
//
//    /**
//     * データベースコネクション.
//     */
//    private IDatabaseConnection connection;
//
//    /**
//     * テーブル名を取得する.
//     */
//    static {
//
//        TABLE_NAME_ALL = new String[TABLE_SIZE];
//
//        for (int i = 0; i < TABLE_NAME_ALL.length; i++) {
//            String tableName = TABLE_NAME + (i + 1);
//
//            if (null == tableName) {
//                break;
//            }
//
//            TABLE_NAME_ALL[i] = tableName;
//        }
//    }
//
//    /**
//     * コンストラクタ.
//     *
//     * @param arg0 パラメータ
//     */
//    public DaoBaseTestCase(String arg0) {
//        super(arg0);
//    }
//
//    /**
//     * 事前処理.
//     * 元データを退避し、テストデータを投入する。
//     *
//     * @throws Exception 事前処理中にエラーが発生した
//     */
//    protected void setUp() throws Exception {
//
//        super.setUp();
//
//        try {
//            connection = this.getConnection();
//
//            QueryDataSet queryDataSet = new QueryDataSet(connection);
//            for (int i = 0; i < TABLE_NAME_ALL.length; i++) {
//                queryDataSet.addTable(TABLE_NAME_ALL[i]);
//            }
//            this.file = File.createTempFile("tmp", ".xml");
//            FlatXmlDataSet.write(queryDataSet, new FileOutputStream(this.file));
//
//            DatabaseOperation.CLEAN_INSERT.execute(connection, this
//                    .getDataSet(getTestFileName()));
//
//        } catch (Exception e) {
//            if (connection != null) {
//                connection.close();
//            }
//            throw e;
//        }
//    }
//
//    /**
//     * 事後処理.
//     * 元データを復元する。
//     *
//     * @throws Exception 事後処理中にエラーが発生した
//     */
//    protected void tearDown() throws Exception {
//
//        super.tearDown();
//
//        try {
//            DatabaseOperation.CLEAN_INSERT.execute(connection,
//                    new FlatXmlDataSet(this.file));
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//    /**
//     * DBコネクションを取得する.
//     *
//     * @return DBコネクション
//     */
//    protected IDatabaseConnection getConnection() {
//
//        return ConnectionManager.getConnection();
//    }
//
//    /**
//     * データベースに登録されているデータを取得する.
//     *
//     * @param tableName テーブル名
//     * @return データベースに登録されているデータ
//     * @throws SQLException データベースに登録されているデータの
//     * 取得中にエラーが発生した
//     */
//    protected ITable getActualTable(String tableName) throws Exception {
//
//        IDatabaseConnection connection = null;
//
//        try {
//            connection = getConnection();
//            return connection.createDataSet().getTable(tableName);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//    /**
//     * テスト結果として期待されるデータを取得する.
//     *
//     * @param tableName テーブル名
//     * @param fileName テストファイル名
//     * @return テスト結果として期待されるデータ
//     * @throws Exception データの取得中にエラーが発生した
//     */
//    protected ITable getExpectedTable(String tableName, String fileName)
//            throws Exception {
//
//        return this.getDataSet(fileName).getTable(tableName);
//    }
//
//    /**
//     * データベースに登録されているデータ件数を取得する.
//     *
//     * @param tableName テーブル名
//     * @return データ件数
//     * @throws Exception データ件数の取得中にエラーが発生した
//     */
//    protected int getTableRowCount(String tableName) throws Exception {
//
//        IDatabaseConnection connection = null;
//
//        try {
//            connection = getConnection();
//            return connection.createDataSet().getTable(tableName).getRowCount();
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//    /**
//     * 期待値とデータベースに登録されているデータの比較をする.
//     *
//     * @param expectedTable 期待値データ
//     * @param actualTable データベースに登録されているデータ
//     * @throws DataSetException データの比較中にエラーが発生した
//     */
//    protected void assertEquals(ITable expectedTable, ITable actualTable)
//            throws DataSetException {
//
//        for (int i = 0; i < expectedTable.getRowCount(); i++) {
//            for (int j = 0; j < expectedTable.getTableMetaData().getColumns().length; j++) {
//                Column[] columns = expectedTable.getTableMetaData()
//                        .getColumns();
//                String columnName = columns[j].getColumnName();
//
//                if (this.isSkipColumn(columnName)) {
//                    continue;
//                }
//
//                assertEquals("Line:" + (i + 1) + " Column:" + columnName,
//                        convertData(expectedTable.getValue(i, columnName)),
//                        convertData(actualTable.getValue(i, columnName)));
//            }
//        }
//    }
//
//    /**
//     * assertTableメソッド実行時、比較をスキップするカラム名を指定する.
//     *
//     * @param columnName カラム名
//     * @return true：スキップ、false：スキップなし
//     *
//     */
//    protected boolean isSkipColumn(String columnName) {
//        return false;
//    }
//
//    /**
//     * 期待値とEntityデータの比較をする.
//     *
//     * @param expectedTable 期待値データ
//     * @param actuals 実際のデータ
//     * @throws Exception 期待値とEntityデータの比較中にエラーが発生した
//     */
//    protected void assertEquals(ITable expectedTable, Object actuals)
//            throws Exception {
//
//        this.assertEquals(expectedTable, new Object[] { actuals });
//    }
//
//    /**
//     * 期待値とEntityデータの比較をする.
//     *
//     * @param expectedTable 期待値データ
//     * @param actuals 実際のデータ
//     * @throws Exception 期待値とEntityデータの比較中にエラーが発生した
//     */
//    protected void assertEquals(ITable expectedTable, Object[] actuals)
//            throws Exception {
//
//        for (int i = 0; i < expectedTable.getRowCount(); i++) {
//            for (int j = 0; j < expectedTable.getTableMetaData().getColumns().length; j++) {
//                Column[] columns = expectedTable.getTableMetaData()
//                        .getColumns();
//                String columnName = columns[j].getColumnName();
//                String getterName = this.buildGetterName(columnName);
//
//                Object actual = actuals[i];
//                Class[] paramClass = new Class[0];
//                Object[] paramValue = new Object[0];
//                Method method = actual.getClass().getDeclaredMethod(getterName,
//                        paramClass);
//
//                assertEquals("Line:" + (i + 1) + " Column:" + columnName,
//                        String.valueOf(expectedTable.getValue(i, columnName))
//                                .trim(), String.valueOf(
//                                method.invoke(actual, paramValue)).trim());
//            }
//        }
//    }
//
//    /**
//     * テストデータExcelファイルの名前を取得する.
//     *
//     * @return テストデータExcelファイル名称
//     */
//    protected abstract String getTestFileName();
//
//    /**
//     * 期待値データを取得する.
//     *
//     * @param index インデックス番号
//     * @return 期待値データ
//     * @throws Exception 期待値データの取得中にエラーが発生した
//     */
//    protected abstract ITable getExpectedTable(String index) throws Exception;
//
//    /**
//     * サブクラスの実行時クラスを取得する.
//     *
//     * @return 実行時クラス
//     */
//    protected abstract Class getSubClass();
//
//    /**
//     * ファイル名を指定してデータセットを取得する.
//     *
//     * @param fileName ファイル名
//     * @return データセット
//     * @throws Exception データセットの取得中にエラーが発生した
//     */
//    private IDataSet getDataSet(String fileName) throws Exception {
//
//        ReplacementDataSet expectedDataSet = new ReplacementDataSet(
//                new XlsDataSet(getSubClass().getResourceAsStream(fileName)));
//        expectedDataSet.addReplacementObject("[SYSDATE]", new Date(System
//                .currentTimeMillis()));
//        expectedDataSet.addReplacementObject("[NULL]", null);
//
//        return expectedDataSet;
//    }
//
//    /**
//     * カラム名を指定してgetterメソッド名を取得する.
//     *
//     * @param columnName カラム名
//     * @return getterメソッド名
//     */
//    private String buildGetterName(String columnName) {
//
//        String getterName = "get";
//        String temp = columnName;
//
//        while (true) {
//            int index = temp.indexOf("_");
//
//            if (index == -1) {
//                getterName += temp.substring(0, 1).toUpperCase();
//                getterName += temp.substring(1).toLowerCase();
//                break;
//            }
//
//            getterName += temp.substring(0, 1).toUpperCase();
//            getterName += temp.substring(1, index).toLowerCase();
//            temp = temp.substring(index + 1);
//        }
//
//        return getterName;
//    }
//
//    /**
//     * データを変換する.
//     *
//     * @param data データ
//     * @return 変換後データ
//     */
//    private Object convertData(Object data) {
//
//        if (data instanceof String) {
//            String dataStr = ((String) data).trim();
//            if ("true".equalsIgnoreCase(dataStr)) {
//                return Boolean.TRUE;
//            } else if ("false".equalsIgnoreCase(dataStr)) {
//                return Boolean.FALSE;
//            }
//            return dataStr;
//        } else if (data instanceof BigDecimal) {
//            return new Integer(((BigDecimal) data).intValue());
//        } else {
//            return data;
//        }
//    }
//}
