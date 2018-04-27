import numpy as np
import matplotlib.pyplot as plt

fig = plt.figure()

ax1 = fig.add_subplot(211)
ax1.plot([15,16,18,20,22],[0.0009,0.0009,0.0020,0.0020,0.0020])
ax1.set_title('Dynamic Method')
ax1.set_ylabel('Y axis(Time)')
ax1.set_ylabel('X axis(M+N)')
ax1.set_ylim((0,0.0024))

ax3 = fig.add_subplot(212)
ax3.plot([15,16,18,20,22],[0.17,0.33,1.85,6.95,60.1])
ax3.set_title('Recursive Method')
ax3.set_ylabel('Y axis(Time)')
ax3.set_xlabel('X axis(M+N)')
ax3.set_ylim((0,61.0))

plt.show()