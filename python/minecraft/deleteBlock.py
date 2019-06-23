from mcpi import minecraft
import random

mc = minecraft.Minecraft.create()
x,y,z = mc.player.getPos()
x = x +random.randint(-10,10)
z = z +random.randint(-10,10)
for i in range(0,21):
    mc.setBlock(x,y+i,z,random.randint(1,8))
    
