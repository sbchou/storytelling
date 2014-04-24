import newspaper

nytimesUrl = 'http://nytimes.com/'

print 'Fetching articles from ' + nytimesUrl

nytimes = newspaper.build(nytimesUrl, memoize_articles=False)

print 'Loaded ', len(nytimes.articles), ' articles'

counts = {}

for article in nytimes.articles:
	
	try:

		article.download()
		article.parse()
		article.nlp()

		print article.keywords

		for keyword in article.keywords:

			if keyword in counts:
				counts[keyword] += 1
			else:
				counts[keyword] = 1

		printCount = 0

		for key in sorted(counts, key=counts.get, reverse=True):

			print counts[key], ':', key

			printCount += 1

			if printCount > 30:
				break

	except:

		print 'Error in article: ', article.url
