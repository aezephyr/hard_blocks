##_**Hard Blocks**_ is an attempt to encourage caving over branch mining.

_**Hard Blocks**_ makes the blocks listed in the `hard_blocks.json` config file harder to mine.
By default, the only effected blocks are: 
`stone`, `granite`, `diorite`, `andesite`, `tuff`, `deepslate`, `dripstone_block`, and `netherrack`


These blocks take longer to mine when their neighbors are made of the same material.

A block with _no_ similar neighbors can be broken normally.
A block touching only _one_ other block of the same material takes somewhat longer to break.
A block that has _two_ similar neighbors takes much longer to break.
A block with _three_ similar neighbors will likely make a player decide to break something  else, 
and blocks with _four_ or more neighbors are essentially unbreakable.

This is my first mod and is essentially a tutorial exercise gone haywire.