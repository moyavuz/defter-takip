import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

export interface IMalzeme {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    fiyatMaliyet?: number;
    malzemeMalzemeTakips?: IMalzemeTakip[];
    birim?: IBirim;
    grups?: IMalzemeGrubu[];
}

export class Malzeme implements IMalzeme {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public fiyatMaliyet?: number,
        public malzemeMalzemeTakips?: IMalzemeTakip[],
        public birim?: IBirim,
        public grups?: IMalzemeGrubu[]
    ) {}
}
