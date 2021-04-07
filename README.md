## 設定方法

https://www.ibm.com/docs/ja/was-liberty/base?topic=liberty-enabling-jdbc-tracing
server.xml(`traceLevel`) と bootstrap.properties に設定が必要。liberty ディレクトリのサンプルファイルを参照。

## アプリの稼働方法

```bash
curl -X POST http://localhost:9080/jdbc-trace/MyServlet
```

## 出力先

usr/servers/defaultServer/logs/trace.log にトレースが出力される

## Db2 の準備

DockerHub から pull してコンテナを起動した後、`db2sampl`コマンドで SAMPLE データベースを作成する必要あり。
