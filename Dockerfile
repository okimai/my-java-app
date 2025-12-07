# ベースステージ
FROM maven:3.9.11-amazoncorretto-24-debian AS base
# コンテナの作業ディレクトリを指定
WORKDIR /app
# Maven が pom.xml に記載されたすべての依存ライブラリを事前にダウンロードしてローカルキャッシュ（~/.m2）に保存する
# これにより、後続のビルド工程（mvn install など）でソースコードが変更されても、依存ライブラリの再ダウンロードを避けることができ、ビルド時間を短縮できます。
COPY pom.xml ./
RUN mvn dependency:go-offline
# アプリケーションのソースコードを作業ディレクトリにコピー
COPY src ./src
# pom.xmlに従ってビルドを行い、jarファイルを作成
RUN mvn install

# 開発ステージ(ベースステージ、開発ステージを実行)
FROM base AS development
# 開発サーバー用にポート8080を公開
EXPOSE 8080
# コンテナ起動時、Spring Bootアプリケーションを起動
CMD ["mvn", "spring-boot:run"]

# 解凍ステージ
FROM base AS extract
# jarファイルを展開するためのフォルダーを作成し、
# jarファイルをレイヤーごとに展開(アプリ本体のクラスやリソースが、依存ライブラリと分離して解凍される)
RUN cp -p /app/target/*.jar /app/target/application.jar
RUN java -Djarmode=tools -jar /app/target/application.jar extract --layers --destination /app/extracted

# 検証ステージ(ベースステージ、解凍ステージ、検証ステージを実行)
# Java 24のdebianベースのイメージを使用
# amazoncorrettoを使用しない理由は、non-root userの作成に必要なパッケージが含まれていないため
FROM eclipse-temurin:24.0.2_12-jre-noble AS production
# コンテナの作業ディレクトリを指定
WORKDIR /application
# non-root user、groupを作成
RUN groupadd -r mygroup && \
    useradd -m -r -g mygroup myuser
# 解凍ステージで展開した各レイヤーをコンテナにコピー
# (/app/extracted/application/ に application.jar が含まれる)
COPY --from=extract --chown=myuser:mygroup /app/extracted/dependencies/ ./
COPY --from=extract --chown=myuser:mygroup /app/extracted/spring-boot-loader/ ./
COPY --from=extract --chown=myuser:mygroup /app/extracted/snapshot-dependencies/ ./
COPY --from=extract --chown=myuser:mygroup /app/extracted/application/ ./
# non-root userに変更
USER myuser
# ポート8080を外部に公開
EXPOSE 8080
# コンテナ起動時、Spring Bootアプリケーションを起動
ENTRYPOINT ["java", "-jar", "application.jar"]
