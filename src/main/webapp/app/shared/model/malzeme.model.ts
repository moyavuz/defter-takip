import { IBirim } from 'app/shared/model/birim.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

export interface IMalzeme {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    fiyatGelis?: number;
    aktifMi?: boolean;
    birim?: IBirim;
    poz?: IPoz;
    iscilik?: IIscilik;
    grups?: IMalzemeGrubu[];
}

export class Malzeme implements IMalzeme {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public fiyatGelis?: number,
        public aktifMi?: boolean,
        public birim?: IBirim,
        public poz?: IPoz,
        public iscilik?: IIscilik,
        public grups?: IMalzemeGrubu[]
    ) {
        this.aktifMi = this.aktifMi || false;
    }
}
