package dbt;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBUnitTest{

	private IDatabaseTester databaseTester;
	private IDatabaseConnection connection;

	public DBUnitTest() throws Exception {
		//テストクラスをインスタンス化するときに、DBに接続するためのtesterを作成する
		databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/dbunit", "root", "1qazxsw2");
	}

	@Before
	public void before() throws Exception {
		//テーブルに初期化用のデータを投入する
		IDataSet dataSet =
				new FlatXmlDataSetBuilder().build(new File("data/init.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);

		databaseTester.onSetup();
	}

	@After
	public void after() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.NONE);
		databaseTester.onTearDown();
	}

	@Test
	public void compareTable() throws Exception {
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("member");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("data/expected.xml"));
		ITable expectedTable = expectedDataSet.getTable("member");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void insertTable() throws Exception {
		IDatabaseConnection testerConnection = databaseTester.getConnection();
		Connection con = testerConnection.getConnection();
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();

		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into member(id,name,birth) values('0004','yuka','1990-05-10')");

		ITable actualTable = databaseDataSet.getTable("member");
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("data/expected2.xml"));
		ITable expectedTable = expectedDataSet.getTable("member");

	}


}