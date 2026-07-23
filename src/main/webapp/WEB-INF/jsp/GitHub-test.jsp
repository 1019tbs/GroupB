<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>GitHubコマンド学習記録</title>

<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	background-color: #f5f7f9;
	color: #333333;
	font-family: "Yu Gothic", "Meiryo", sans-serif;
	line-height: 1.8;
}

.header {
	background-color: #24292f;
	color: #ffffff;
	padding: 35px 20px;
	text-align: center;
}

.header__title {
	margin: 0;
	font-size: 32px;
}

.header__text {
	margin-top: 10px;
	margin-bottom: 0;
	color: #d0d7de;
}

.main {
	width: 90%;
	max-width: 1000px;
	margin: 40px auto;
}

.section {
	margin-bottom: 35px;
	padding: 30px;
	background-color: #ffffff;
	border-radius: 12px;
	box-shadow: 0 3px 12px rgba(0, 0, 0, 0.08);
}

.section__title {
	margin-top: 0;
	padding-bottom: 10px;
	border-bottom: 3px solid #2da44e;
	font-size: 25px;
}

.command-list {
	margin: 0;
	padding: 0;
	list-style: none;
}

.command-item {
	margin-bottom: 30px;
	padding-bottom: 25px;
	border-bottom: 1px solid #d8dee4;
}

.command-item:last-child {
	margin-bottom: 0;
	padding-bottom: 0;
	border-bottom: none;
}

.command-item__title {
	margin-top: 0;
	margin-bottom: 10px;
	font-size: 20px;
}

.command-box {
	position: relative;
	margin: 12px 0;
	padding: 16px 20px;
	overflow-x: auto;
	background-color: #161b22;
	border-radius: 8px;
	color: #f0f6fc;
	font-family: Consolas, Monaco, monospace;
	font-size: 15px;
	white-space: pre;
}

.command-item__description {
	margin-bottom: 0;
}

.example {
	margin-top: 12px;
	padding: 15px;
	background-color: #f6f8fa;
	border-left: 5px solid #2da44e;
	border-radius: 5px;
}

.example__title {
	margin-top: 0;
	margin-bottom: 5px;
	font-weight: bold;
}

.point-list {
	padding-left: 25px;
}

.point-list li {
	margin-bottom: 10px;
}

.warning {
	padding: 20px;
	background-color: #fff8c5;
	border: 1px solid #d4a72c;
	border-radius: 8px;
}

.warning__title {
	margin-top: 0;
	color: #9a6700;
}

.flow {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	justify-content: center;
	gap: 12px;
}

.flow__item {
	padding: 12px 18px;
	background-color: #ddf4ff;
	border: 1px solid #54aeff;
	border-radius: 8px;
	font-weight: bold;
	text-align: center;
}

.flow__arrow {
	font-size: 22px;
	font-weight: bold;
}

.footer {
	margin-top: 50px;
	padding: 25px;
	background-color: #24292f;
	color: #ffffff;
	text-align: center;
}

@media screen and (max-width: 600px) {
	.header__title {
		font-size: 25px;
	}

	.main {
		width: 94%;
		margin-top: 25px;
	}

	.section {
		padding: 20px;
	}

	.section__title {
		font-size: 21px;
	}

	.command-box {
		font-size: 13px;
	}

	.flow {
		flex-direction: column;
	}

	.flow__arrow {
		transform: rotate(90deg);
	}
}
</style>

</head>

<body>

	<header class="header">
		<h1 class="header__title">GitHubコマンド学習記録</h1>
		<p class="header__text">2026年7月23日に行ったGit・GitHub操作のまとめ</p>
	</header>

	<main class="main">

		<section class="section">
			<h2 class="section__title">作業開始時の流れ</h2>

			<div class="flow">
				<div class="flow__item">状態確認</div>
				<div class="flow__arrow">→</div>
				<div class="flow__item">mainへ移動</div>
				<div class="flow__arrow">→</div>
				<div class="flow__item">最新状態を取得</div>
				<div class="flow__arrow">→</div>
				<div class="flow__item">自分のブランチへ移動</div>
				<div class="flow__arrow">→</div>
				<div class="flow__item">mainをマージ</div>
			</div>
		</section>

		<section class="section">
			<h2 class="section__title">作業開始時のコマンド</h2>

			<ol class="command-list">

				<li class="command-item">
					<h3 class="command-item__title">① プロジェクトフォルダへ移動</h3>

					<div class="command-box">cd C:\pleiades\2026-03\workspace\GroupB</div>

					<p class="command-item__description">
						コマンドを実行する対象のプロジェクトフォルダへ移動します。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">② 現在の状態を確認</h3>

					<div class="command-box">git status</div>

					<p class="command-item__description">
						変更中のファイルや、コミットされていないファイルがないか確認します。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">③ mainブランチへ切り替える</h3>

					<div class="command-box">git switch main</div>

					<p class="command-item__description">
						最新のデータを取得するため、一度mainブランチへ移動します。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">④ 最新のmainを取得</h3>

					<div class="command-box">git pull origin main</div>

					<p class="command-item__description">
						GitHubに保存されている最新のmainブランチを取得します。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">⑤ 自分のブランチへ切り替える</h3>

					<div class="command-box">git switch feature/自分のブランチ名</div>

					<div class="example">
						<p class="example__title">実行例</p>
						<code>git switch feature/tabuse</code>
					</div>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">⑥ 現在のブランチを確認</h3>

					<div class="command-box">git branch</div>

					<p class="command-item__description">
						先頭に「*」が付いているものが、現在使用しているブランチです。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">⑦ mainを自分のブランチへ取り込む</h3>

					<div class="command-box">git merge main</div>

					<p class="command-item__description">
						最新のmainブランチの内容を、自分の作業ブランチへ反映します。
					</p>
				</li>

			</ol>
		</section>

		<section class="section">
			<h2 class="section__title">変更内容を確認するコマンド</h2>

			<ol class="command-list">

				<li class="command-item">
					<h3 class="command-item__title">すべての変更を確認</h3>

					<div class="command-box">git diff</div>

					<p class="command-item__description">
						編集したファイルの変更箇所を確認します。
					</p>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">ファイルを指定して確認</h3>

					<div class="command-box">git diff ファイル名</div>

					<div class="example">
						<p class="example__title">pom.xmlを確認する場合</p>
						<code>git diff pom.xml</code>
					</div>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">Gitの管理対象外ファイルを確認</h3>

					<div class="command-box">type .gitignore</div>

					<div class="example">
						<p class="example__title">今回の内容</p>
						<code>/target/</code>
					</div>

					<p class="command-item__description">
						「/target/」と書かれているため、targetフォルダはGitHubへアップロードされません。
					</p>
				</li>

			</ol>
		</section>

		<section class="section">
			<h2 class="section__title">変更をGitHubへ送るコマンド</h2>

			<ol class="command-list">

				<li class="command-item">
					<h3 class="command-item__title">① 変更したファイルを追加</h3>

					<div class="command-box">git add .</div>

					<p class="command-item__description">
						現在のプロジェクト内にある変更ファイルを、コミット対象へ追加します。
					</p>

					<div class="example">
						<p class="example__title">ファイルを指定する場合</p>
						<code>git add pom.xml</code>
					</div>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">② 変更内容を保存</h3>

					<div class="command-box">git commit -m "変更内容を入力"</div>

					<div class="example">
						<p class="example__title">実行例</p>
						<code>git commit -m "プロジェクト名をGroupBに変更"</code>
					</div>
				</li>

				<li class="command-item">
					<h3 class="command-item__title">③ GitHubへ送信</h3>

					<div class="command-box">git push origin feature/自分のブランチ名</div>

					<div class="example">
						<p class="example__title">実行例</p>
						<code>git push origin feature/tabuse</code>
					</div>
				</li>

			</ol>
		</section>

		<section class="section">
			<h2 class="section__title">今回変更した内容</h2>

			<div class="example">
				<p class="example__title">プロジェクト名の変更</p>

				<p>インポートしたプロジェクトの名前が異なっていたため、次のように変更しました。</p>

				<div class="command-box">&lt;artifactId&gt;GroupB_Project&lt;/artifactId&gt;
↓
&lt;artifactId&gt;GroupB&lt;/artifactId&gt;

&lt;name&gt;GroupB_Project&lt;/name&gt;
↓
&lt;name&gt;GroupB&lt;/name&gt;</div>
			</div>
		</section>

		<section class="section">
			<h2 class="section__title">Gitコマンド一覧</h2>

			<ul class="point-list">
				<li><strong>git status</strong>：現在の変更状態を確認する</li>
				<li><strong>git switch</strong>：ブランチを切り替える</li>
				<li><strong>git branch</strong>：ブランチの一覧を確認する</li>
				<li><strong>git pull</strong>：GitHubから最新データを取得する</li>
				<li><strong>git merge</strong>：別のブランチの内容を取り込む</li>
				<li><strong>git diff</strong>：ファイルの変更箇所を確認する</li>
				<li><strong>git add</strong>：変更ファイルをコミット対象へ追加する</li>
				<li><strong>git commit</strong>：変更履歴を保存する</li>
				<li><strong>git push</strong>：変更履歴をGitHubへ送信する</li>
			</ul>
		</section>

		<section class="section">
			<div class="warning">
				<h2 class="warning__title">注意事項</h2>

				<ul class="point-list">
					<li>作業開始前は、必ずmainブランチを最新の状態にします。</li>
					<li>プログラムの編集は、自分の作業ブランチで行います。</li>
					<li>コマンド実行前後に、git statusで状態を確認します。</li>
					<li>競合が発生した場合は、勝手に処理せずメンバーへ共有します。</li>
					<li>pushする前に、変更内容とブランチ名を確認します。</li>
				</ul>
			</div>
		</section>

	</main>

	<footer class="footer">
		<p>GitHub学習記録</p>
	</footer>

</body>

</html>