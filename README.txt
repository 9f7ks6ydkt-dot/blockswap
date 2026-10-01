Block Swap - Fabric 1.21.11 (client only)
Press I in game: diamond blocks look like gold and gold blocks look like diamond.
Press I again to go back to normal. Rebind in Options > Controls > Block Swap.

BUILD WITH GITHUB (no installs):
1. Create a free repo on github.com and upload everything in this folder
   (including the hidden .github folder).
2. Open the Actions tab, wait for "Build mod" to finish (green check).
3. Open the run, download the "blockswap-jar" artifact, unzip it.
4. Use blockswap-1.0.0.jar (NOT the -sources jar) in your mods folder.

BUILD ON YOUR PC: install Java 21 + Gradle 9.2+, run "gradle build",
jar appears in build/libs/.

NEEDS: Fabric Loader 0.18+ and Fabric API 0.141.x for 1.21.11.
Visual only. Check Donut SMP's rules on client mods.
