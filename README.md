# Distributed Chord Algorithm Implementation

## Team Members

| Github | Website|
|------------- | -------------|
| [Deividas Mickevicius](https://github.com/davidmickev)            |  [davidmickev.github.io](https://davidmickev.github.io)            |
|   [Jacob Sanchez](https://github.com/jsanchez78)            |  [jacobsanchez.dev](https://www.jacobsanchez.dev)            |
|  [Alex Jalomo](https://github.com/Jalomo1197)            |  [jalomo1197.github.io](https://jalomo1197.github.io/Portfolio/)            |


## Prerequisites
Installed [sbt-1.3.13](https://www.scala-sbt.org/download.html) 

## Instructions
* Download the project [source](https://github.com/Jalomo1197/DistributedChordAlgorithmImplementation) 

* or use
`git clone https://mttdavid@bitbucket.org/jsanch75/group_13.git`

* Open/import the project in your IDE:

* Open Terminal and build the project using SBT

`sbt clean compile run`

* Alternate method: Run the main Scala class ``
Driver
``

## Citation
Software and design was based on: Chord: A Scalable Peer-to-peer Lookup Service for Internet Applications and current version of akka.
The source to the documentation can be found at:


https://pdos.csail.mit.edu/papers/chord:sigcomm01/chord_sigcomm.pdf <br>
https://doc.akka.io/docs/akka/current/

## Visualization with R

Utilizing the Circilize methodology we map the values from the configuration file into R representing which location each Movie gets assigned to
the chord. There were 5 different algorithm procedures in R all utilizing the SHA-1 methodology to encrypt the name.
The results on the image become very pixelated as the size of entries get larger.

![Alt text](Rplot.png?raw=true "Title")


## Design Architecture and Implementations
We are using typed Akka Behaviors model system to interact and manipulate Chord through actors.


The data that is stored in the Chord will be a list of movie data.
The key(movieName) node will store the values(year,$revenue) of that movie.


The movies dataset that was used can be found on [kaggle](https://www.kaggle.com/rounakbanik/the-movies-dataset?select=movies_metadata.csv)
and was parsed with the parser.py script located under /group_13/ that takes the first valid 250+ movies that have a title and budget/year.

#### Each bulletin will describe the implementation of the System Model
`Load balance:` 

Chord acts as a distributed hash function,
spreading keys evenly over the nodes; this provides a degree
of natural load balance.

* The application.conf contains N entries for the chord.
* For each item or for a subset of items we `join` the node 

The consistent hash location function assigns each node and key an m-bit identifier using a base hash function SHA-1 found under `hash` file


* A node’s identifier is chosen by hashing the node’s (movie name) key, while which refers to the value in the key resulting (year,$revenue)


`Decentralization:` 


Homework 3 Chord is partial-distributed: The first node gets the role of `manager`
which is a reference to the loosely-organized peer-to-peer application for a reference to where nodes are and functionality of managing other nodes.

* Identifiers are ordered in an identifier circle modulo 2^(m) Key k is assigned to the first node whose identifier is equal to or follow


`Availability:` 


* Chord automatically adjusts its internal tables utilizing `update others` when a node joins or is removed.


The reflected newly joined nodes as well as node failures, ensuring that the node responsible for a key can always be found when the network is changed.

* New nodes learns the identity of an existing Chord node (N~) = masterNode`(MN)` by having reference to the manager node.
New node (N) initialize its state and add itself to the existing Chord network, as follows.

* Initializing fingers and predecessor: Node N learns its predecessor and fingers by asking `MN` to look them up, using the
init finger table, find successor for each of the (m) finger entries would give a runtime of O(m log N)

* Updating fingers of existing nodes: Node (N) will need to be entered into the finger tables of some existing nodes utilizing the `update_finger_table` function.