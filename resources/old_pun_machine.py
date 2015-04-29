#!/usr/bin/env python
import cgi
import re
import os
import urllib
from string import ascii_letters
title = 'The Pun Machine 1.1'
params = cgi.FieldStorage()

print 'Content-Type: text/html\n\n'
print '<!DOCTYPE html><html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:fb="http://www.facebook.com/2008/fbml"><head><title>' + title + '</title><meta name="viewport" content="width=320" />'
print '<link rel="icon" type="image/png" href="http://puns.ericb.me/50percentgrey.png" />'

if 'q' in params.keys():
	print '<meta property="og:title" content="' + params['q'].value + '"/>'
print '<meta property="og:url" content="http://puns.ericb.me/?q=' + os.environ["REQUEST_URI"][4:] + '">'
print '<meta property="og:site_name" content="' + title + '"/>'
print '<meta property="og:description" content="The greyest thing you\'ve ever."/>'
print '<meta property="og:type" content="website"/>'
print '<meta property="og:image" content="http://puns.ericb.me/50percentgrey.png"/>'
print '</head><body style="text-align: center">'
print '<p>'


cons = list('BCDFGHJKLMNPQRSTVWXZ')
id_tab = "".join(map(chr, xrange(256)))
tostrip = "".join(c for c in id_tab if c.isalpha())

s = 'daslfkj%1234lkasjd^*)(_'
if 'q' in params.keys():
	words = params['q'].value.split()
	
	with open('cmudict.0.7a.txt', 'r') as inF:
		for line in inF:
			if not line[0].isalpha():
				continue
			for i,word in enumerate(words):
				word = filter(lambda c: c.isalpha(), word)
				if len(word) > 3 and line.startswith(word.upper() + '  ') and ' EY' in line:
					pun = ''
					syllables = line.split()[1:]
					for j,s in enumerate(syllables):
						if j+1 < len(syllables) and syllables[j+1].startswith('EY'):
							pun += (s + ' G R' if s not in cons else ' G R')
						else:
							pun += ' ' + s
					words[i] = pun.replace("0","").replace("1","").replace("2","").replace("G G","G").replace("EY T","EY").replace("G R EY AH S T", "<b>greyest</b>").replace("B G R EY K", "<b>Bgreyk</b>").replace("G R EY V ER IH T", "<b>greyvorite</b>").replace("G R EY ER", "<b>greyer</b>").replace("G R EY","<b>grey</b>").replace("<b>grey</b> S IH K L IY", "<b>greysically</b>").replace("<b>grey</b> F L IY", "<b>greyfly</b>");

print ' '.join(words).replace("May","<b>Grey</b>")
print '<form action="" method="get">'
print 'Another: <input type="text" name="q" />'
print '<input type="submit" value="#GETIT" />'
print '</form>'
print '</p>'
print '</body><footer><p>brought to you by <a href="http://www.endoftheinternet.com" target="_blank">no@thanks.com</a></p></footer></html>'