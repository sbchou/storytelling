import newspaper
import io
import sys

nytimesUrl = 'http://nytimes.com/'

print 'Fetching articles from ' + nytimesUrl

nytimes = newspaper.build(nytimesUrl, memoize_articles=False)

count = 0;

for article in nytimes.articles:
	
	print 'Article ' + str(count) + ': ' + article.url

	try:

		article.download()
		article.parse()

		filename = '/home/dan/data/storytelling/articles/article_' + str(count) + '.txt'

		f = open(filename, 'w')

		encodedText = article.text.encode('utf8')

		f.write(encodedText)

		count += 1

		f.close();
  
	except:

		print 'ERROR'
