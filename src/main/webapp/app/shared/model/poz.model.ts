import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IPozGrubu } from 'app/shared/model/poz-grubu.model';

export interface IPoz {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    yil?: number;
    tenzilatsizFiyat?: number;
    tenzilatliFiyat?: number;
    taseronFiyat?: number;
    tasereFiyat?: number;
    malzemeMi?: boolean;
    hakedisDetays?: IHakedisDetay[];
    birim?: IBirim;
    grups?: IPozGrubu[];
}

export class Poz implements IPoz {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public yil?: number,
        public tenzilatsizFiyat?: number,
        public tenzilatliFiyat?: number,
        public taseronFiyat?: number,
        public tasereFiyat?: number,
        public malzemeMi?: boolean,
        public hakedisDetays?: IHakedisDetay[],
        public birim?: IBirim,
        public grups?: IPozGrubu[]
    ) {
        this.malzemeMi = this.malzemeMi || false;
    }
}
