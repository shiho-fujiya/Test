//package dbunit_sample;
//
//import org.dbunit.dataset.ITable;
//
///**
// * SampleDaoImplクラスのテスト.
// */
//public class SampleDaoImplTest extends DaoBaseTestCase {
//
//    private static final String TABLE_NAME = TABLE_NAME_ALL[1];
//
//    private SampleDao sampleDao;
//
//    public static void main(String[] args) {
//        junit.textui.TestRunner.run(SampleDaoImplTest.class);
//    }
//
//    public SampleDaoImplTest(String arg0) {
//        super(arg0);
//    }
//
//    protected void setUp() throws Exception {
//        super.setUp();
//
//        this.sampleDao = new SampleDaoImpl(getConnection().getConnection());
//    }
//
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
//
//    /**
//     * getDataメソッドの動作確認.
//     * データが取得されていることを確認する。
//     * @throws Exception
//     *
//     */
//    public void testGetData() throws Exception {
//
//        assertEquals(getExpectedTable("1"), sampleDao.getData());
//    }
//
//    /**
//     * Insertメソッドの動作確認.
//     * データが登録されていることを確認する。
//     *
//     */
//    public void testInsert() {
//
//        try {
//            Syohin syohin = new Syohin();
//            syohin.setId("5");
//            syohin.setName("syohin5");
//            syohin.setPrice(new Integer(5000));
//
//            // ロジックの実行
//            sampleDao.insert(syohin);
//
//            assertEquals(5, getTableRowCount(TABLE_NAME));
//
//            // 期待値とデータの比較
//            assertEquals(getExpectedTable("1"), getActualTable(TABLE_NAME));
//
//        } catch (Throwable e) {
//            fail(e.getMessage());
//        }
//    }
//
//    /**
//     * テストデータExcelファイルの名前を取得する.
//     *
//     * @return テストデータExcelファイル名称
//     */
//    protected String getTestFileName() {
//        return "TestSampleDaoImpl_prepare.xls";
//    }
//
//    /**
//     * 期待値データを取得する.
//     *
//     * @param index インデックス番号
//     * @return 期待値データ
//     * @throws Exception 期待値データの取得中にエラーが発生した
//     */
//    protected ITable getExpectedTable(String index) throws Exception {
//
//        return getExpectedTable(TABLE_NAME_ALL[1] + index,
//                "TestSampleDaoImpl_expected.xls");
//    }
//
//    /**
//     * サブクラスの実行時クラスを取得する.
//     *
//     * @return 実行時クラス
//     */
//    protected Class getSubClass() {
//
//        return this.getClass();
//    }
//}