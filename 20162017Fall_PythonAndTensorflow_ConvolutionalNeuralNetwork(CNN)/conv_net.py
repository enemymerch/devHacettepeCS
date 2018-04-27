import tensorflow as tf

from tensorflow.examples.tutorials.mnist import input_data


mnist = input_data.read_data_sets("/temp/data/", one_hot=True)

#10 classes, 0-9
ClassNumber = 10
BatchSize = 128

# height x width
x = tf.placeholder(tf.float32, [None, 784])
y = tf.placeholder(tf.float32, [None, 10])



def weight_variable(shape):
  initial = tf.truncated_normal(shape, stddev=0.1)
  return tf.Variable(initial)

def bias_variable(shape):
  initial = tf.constant(0.1, shape=shape)
  return tf.Variable(initial)

def conv2d(x, W):
    return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')


def maxpool2d(x):
    return tf.nn.max_pool(x, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')


def convolutional_neural_network(x):
    weights = {'W_conv1': weight_variable([5, 5, 1, 32]),
               'W_conv2': weight_variable([5, 5, 32, 64]),
               'W_fc': weight_variable([7*7*64, 1024]),
               'out': weight_variable([1024, ClassNumber])}
    biases = {'B_conv1': bias_variable([32]),
              'B_conv2': bias_variable([64]),
              'B_fc': bias_variable([1024]),
              'out': bias_variable([ClassNumber])}

    x = tf.reshape(x, shape=[-1, 28, 28, 1])

    # convolutional layer 1
    conv1 = tf.nn.relu( conv2d(x, weights['W_conv1']) + biases['B_conv1'])
    #pooling layer 1
    conv1 = maxpool2d(conv1)

    # convolutional layer 2sess = tf.InteractiveSession()
    conv2 = tf.nn.relu(conv2d(conv1, weights['W_conv2']) + biases['B_conv2'])
    #pooling layer 2
    conv2 = maxpool2d(conv2)

    fc = tf.reshape(conv2, [-1, 7*7*64])

    fc = tf.nn.relu(tf.matmul(fc, weights['W_fc'])+biases['B_fc'])

    output = tf.matmul(fc, weights['out'])+biases['out']

    return output



def train_neural_network(x):
    prediction = convolutional_neural_network(x)
    cost = tf.reduce_mean( tf.nn.softmax_cross_entropy_with_logits(prediction, y))

    # learnin_Rate = 0.001
    optimizer = tf.train.AdamOptimizer().minimize(cost)

    hm_epochs = 10

    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())

        for epoch in range(hm_epochs):
            epoch_loss = 0
            for _ in range(int(mnist.train.num_examples/BatchSize)):
                epoch_x, epoch_y = mnist.train.next_batch(BatchSize)
                print(epoch_x.shape)
                _, c = sess.run([optimizer, cost], feed_dict={x: epoch_x, y: epoch_y})
                epoch_loss += c

            print('Epoch ', epoch, ' completed out of ', hm_epochs, ' loss : ', epoch_loss)

        correct = tf.equal(tf.argmax(prediction, 1), tf.argmax(y, 1))
        accuracy = tf.reduce_mean(tf.cast(correct, 'float'))
        print('Accuracy: ', accuracy.eval({x:mnist.test.images, y:mnist.test.labels}))


train_neural_network(x)
