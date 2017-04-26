

Note: This requires a running elasticsearch. Just specify the "clustername" and "uri" in the application.conf to connect this to elasticsearch.


Simply run the application when ready.

```
sbt run
```

Create an index to elasticsearch. This index will be named 'library'
```
curl -X POST -v localhost:9000/es/index
```

Inspect the indices in your es node;
```
curl -v localhost:9200/_cat/indices
```

+++

Populate the elasticsearch index with a few books: 
```
curl -XPOST -v localhost:9000/books/populate
```


then, execute a search
```
curl -v "localhost:9000/books?q=life"
```


