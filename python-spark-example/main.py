# Make sure that the spark version matches the version of the spark cluster -> Here Spark 3.4.3 is used
from pyspark.sql import SparkSession

# Important: Make sure that the Python version is supported by the Spark version -> Here Python 3.11 is used
# Important: Make sure that the Java JDK version is supported by the Spark version -> Here JDK 11 is used


def main():
    print("Starting Spark!")

    spark_session = SparkSession.builder \
        .appName("Python Example Driver") \
        .master("spark://127.0.0.1:32077") \
        .getOrCreate()

    # spark_session.sparkContext.setLogLevel("ERROR")

    test(spark_session)

    spark_session.stop()


def test(spark_session):
    data = [("James", "Smith", "USA", "CA"),
            ("Michael", "Rose", "USA", "NY"),
            ("Robert", "Williams", "USA", "CA"),
            ("Maria", "Jones", "USA", "FL")]

    df = spark_session.createDataFrame(data, ["firstname", "lastname", "country", "state"])

    df.show()


if __name__ == "__main__":
    main()
