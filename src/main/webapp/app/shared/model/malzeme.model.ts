import { IBirim } from 'app/shared/model/birim.model';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

export interface IMalzeme {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    fiyatMaliyet?: number;
    birim?: IBirim;
    malzemes?: IMalzemeTakip[];
    grups?: IMalzemeGrubu[];
}

export class Malzeme implements IMalzeme {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public fiyatMaliyet?: number,
        public birim?: IBirim,
        public malzemes?: IMalzemeTakip[],
        public grups?: IMalzemeGrubu[]
    ) {}
}
