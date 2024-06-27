import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]): Unit = {
    println("Starting Spark!")

    val sparkSession = SparkSession.builder()
      .appName("Scala Example Driver")
      .master("spark://127.0.0.1:32077")
      .getOrCreate()

    sparkSession.sparkContext.setLogLevel("ERROR")

    this.test(sparkSession)

    sparkSession.stop()
  }

  def test(spark: SparkSession): Unit = {
    import spark.implicits._

    val data = Seq(
      ("Alice", 21), ("Bob", 22), ("Charlie", 23),
      ("David", 24), ("Eve", 25), ("Frank", 26),
      ("Grace", 27), ("Hank", 28), ("Ivy", 29),
      ("John", 30), ("Kim", 31), ("Liam", 32),
      ("Mona", 33), ("Nora", 34), ("Oscar", 35)
    ).flatMap { case (name, age) =>
      (1 to 1000).map(i => (s"$name$i", age + (i % 10)))
    }

    val df = spark.createDataFrame(data).toDF("Name", "Age")
    val repartitionedDf = df.repartition(2000)

    val joinedDf = repartitionedDf.alias("df1")
      .join(repartitionedDf.alias("df2"), $"df1.Name" === $"df2.Name")
      .select($"df1.Name", $"df1.Age", $"df2.Age".as("Age2"))

    joinedDf.show()

    val complexTransformationDf = joinedDf
      .withColumn("AgeDiff", abs($"Age" - $"Age2"))
      .groupBy($"Name")
      .agg(
        avg($"AgeDiff").as("AvgAgeDiff"),
        max($"AgeDiff").as("MaxAgeDiff")
      )

    println("Complex Transformation Results:")
    complexTransformationDf.show()
  }
}