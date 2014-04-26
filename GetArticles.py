import newspaper
import io

nytimesUrl = 'http://nytimes.com/'

print 'Fetching articles from ' + nytimesUrl

nytimes = newspaper.build(nytimesUrl, memoize_articles=False)

count = 0;

#test = nytimes.articles[0]
#test.download()
#test.parse()

#f = io.open("test.txt", 'w', encoding='utf8')
#text = test.text
#f.write(text)
#f.close()

for article in nytimes.articles:
	
	try:

		article.download()
		article.parse()

		print article.text

		#f = io.open('article_' + count, 'w', encoding='utf8')
		#print "DONE"

		count += 1

		#f.write(article.text)

		#f.close();

		break
  
	except:

		continue
