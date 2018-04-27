import tensorflow as tf
import os, time
import numpy as np

# -----  Reading Images From Disk  ----- #
start_time = time.time()

input_size = 148000

X = np.zeros(shape=(input_size,3072))
Y = np.zeros(shape=(input_size,20))

i = 0;
# Load dataset
for root, dirs, files in os.walk("./results2/"):
    for file in files:

        if ".DS_Store" in file:
            continue

        content = np.fromfile("./results2/" + file, dtype="uint8")
        X[i] = content[20:]
        Y[i] = content[:20]

        i += 1
        if(input_size == i):
            break

print("Reading files: %s" % (time.time() - start_time))
# -----  END Of Read Images From Disk  ----- #


# -----  Parameters  ----- #
learning_rate = 0.001
training_iters = 148000
batch_size = 100
display_step = 10

n_input = 3072 # 32 x 32 x 3
n_classes = 20 # 20 categories
dropout = 0.50

x = tf.placeholder(tf.float32, [None, n_input])
y = tf.placeholder(tf.float32, [None, n_classes])
keep_prob = tf.placeholder(tf.float32) 
# -----  END Of Parameters  ----- #


# -----  Helper Functions  ----- #
def conv2d(x, W):
    return tf.nn.conv2d(x, W, strides=[1,1,1,1], padding='SAME')

def maxpool2d(x):
    return tf.nn.max_pool(x, ksize=[1,2,2,1], strides=[1,2,2,1], padding='SAME')
# -----  END Of Helper Functions  ----- #


# -----  CNN  ----- #
def convolutional_neural_network(x):
    weights = {'w1':tf.Variable(tf.random_normal([11,11,3,32])),
               'w2':tf.Variable(tf.random_normal([5,5,32,64])),
               'w3':tf.Variable(tf.random_normal([3,3,64,256])),
               #'w4':tf.Variable(tf.random_normal([3,3,256,1024])),
               'w_fc_1':tf.Variable(tf.random_normal([4096,4096])),
               'w_fc_2':tf.Variable(tf.random_normal([4096,1024])),
               'out':tf.Variable(tf.random_normal([1024, n_classes]))}

    biases = {'b1':tf.Variable(tf.random_normal([32])),
              'b2':tf.Variable(tf.random_normal([64])),
              'b3':tf.Variable(tf.random_normal([256])),
              #'b4':tf.Variable(tf.random_normal([1024])),
              'b_fc_1':tf.Variable(tf.random_normal([4096])),
              'b_fc_2':tf.Variable(tf.random_normal([1024])),
              'out':tf.Variable(tf.random_normal([n_classes]))}

    # Reshape input
    x = tf.reshape(x, shape=[-1, 32, 32, 3])

    # Layer 1
    conv1 = tf.nn.relu(conv2d(x, weights['w1']) + biases['b1'])
    conv1 = maxpool2d(conv1)

    # Layer 2
    conv2 = tf.nn.relu(conv2d(conv1, weights['w2']) + biases['b2'])
    conv2 = maxpool2d(conv2)

    # Layer 3
    conv3 = tf.nn.relu(conv2d(conv2, weights['w3']) + biases['b3'])
    
    #Bu kısmı kullanmıyoruz
    """
    conv3 = maxpool2d(conv3)

    # Layer 4
    conv4 = tf.nn.relu(conv2d(conv3, weights['w4']) + biases['b4'])
    conv4 = maxpool2d(conv4)
    --------------------------------------------------------------
    """

    # Fully Connected 1
    fc = tf.reshape(conv3,[-1, 4096])
    fc = tf.nn.relu(tf.matmul(fc, weights['w_fc_1'])+biases['b_fc_1'])
    fc = tf.nn.dropout(fc, dropout)

    # Fully Connected 2
    fc2 = tf.reshape(fc,[-1, 4096])
    fc2 = tf.nn.relu(tf.matmul(fc2, weights['w_fc_2'])+biases['b_fc_2'])
    fc2 = tf.nn.dropout(fc2, dropout)

    # Output
    output = tf.matmul(fc2, weights['out'])+biases['out']

    output = tf.sigmoid(output)

    return output
# -----  END Of CNN  ----- #

# -----  Training  ----- #
def train_neural_network(x):
    prediction = convolutional_neural_network(x)
    cost = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(prediction, y))
    optimizer = tf.train.AdamOptimizer().minimize(cost)

    step = 1
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())

        while step * batch_size < training_iters:
            batch_x = X[(step-1)*batch_size : step*batch_size]
            batch_y = Y[(step-1)*batch_size : step*batch_size]

            sess.run(optimizer, feed_dict={x: batch_x, y: batch_y, keep_prob: dropout})
            
            if step % display_step == 0:
                # Calculate and display accuracy and loss
                #correct = tf.equal(tf.argmax(prediction, 1), tf.argmax(y, 1))
                correct = tf.nn.in_top_k(prediction, tf.argmax(y, 1), 1)
               	#3print(sess.run(prediction, feed_dict={x: batch_x}))

                accuracy = tf.reduce_mean(tf.cast(correct, 'float'))
                loss, acc = sess.run([cost, accuracy], feed_dict={x: batch_x, y: batch_y, keep_prob: 1.})
                print("Iterarion: " + str(step*batch_size) + "\nMinibatch Loss: " + "{:.6f}".format(loss) + "\nAccuracy: " + "{:.6f}".format(acc))
            step += 1

        print("Testing Accuracy:", sess.run(accuracy, feed_dict={x: X[:256], y: Y[:256], keep_prob: 1.}))
# -----  END Of Training  ----- #

# Run CNN
train_neural_network(x)
print("Training Network: %s" % (time.time() - start_time))
